    package org.services.baseServices;

    import com.fasterxml.jackson.databind.JsonNode;
    import org.cgi.CgiParameter;
    import org.databases.json.AccountJSONDAO;
    import org.databases.entity.Account;

    import java.util.HashMap;
    import java.util.Map;

    public class    AccountService extends BaseService {

        private AccountJSONDAO accountDAO;

        public AccountService(CgiParameter params) {
            super(params);
            this.accountDAO = new AccountJSONDAO();
        }

        @Override
        public void execute() {
            try {
                String action = getActionFromBody();

                if (action == null || action.isEmpty()) {
                    sendErrorResponse("Keine Action angegeben");
                    return;
                }

                switch(action) {
                    case "login":
                        handleLogin();
                        break;
                    case "register":
                        handleRegister();
                        break;
                    default:
                        sendErrorResponse("Unbekannte Action: " + action);
                }

            } catch (Exception e) {
                System.err.println("ERROR in AccountService:");
                e.printStackTrace();
                sendErrorResponse("Fehler im AccountService: " + e.getMessage());
            }
        }

        private void handleLogin() {
            try {
                JsonNode data = mapper.readTree(params.getContentFromBodyAsString());

                String email = data.get("email").asText();
                String password = data.get("password").asText();

                if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                    sendErrorResponse("Email und Password erforderlich");
                    return;
                }

                Account account = accountDAO.login(email, password);

                if (account == null) {
                    sendErrorResponse("Email oder Password falsch");
                    return;
                }

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login erfolgreich");
                response.put("accountId", account.getAccount_id());
                response.put("surname", account.getSurname());
                response.put("lastname", account.getLastname());
                response.put("phonenumber", account.getPhoneNumber());
                response.put("password", account.getPassword());
                response.put("email", account.getEmail());

                sendJsonResponse(response);

            } catch (Exception e) {
                System.err.println("ERROR in handleLogin:");
                e.printStackTrace();
                sendErrorResponse("Login fehlgeschlagen: " + e.getMessage());
            }
        }

        private void handleRegister() {
            try {
               JsonNode data = mapper.readTree(params.getContentFromBodyAsString());

                String surname = data.get("surname").asText();
                String lastname = data.has("lastname") ? data.get("lastname").asText() : "";
                String email = data.get("email").asText();
                String password = data.get("password").asText();
                String phonenumber = data.has("phonenumber") ? data.get("phonenumber").asText() : "";

                if (email == null || email.isEmpty() || password == null || password.isEmpty() || surname == null || surname.isEmpty()) {
                    sendErrorResponse("Email, Password und Surname sind erforderlich");
                    return;
                }

                if (accountDAO.emailExists(email)) {
                    sendErrorResponse("Email bereits registriert");
                    return;
                }

                Account account = new Account();
                account.setSurname(surname);
                account.setLastname(lastname);
                account.setEmail(email);
                account.setPassword(password);
                account.setPhoneNumber(phonenumber);

                accountDAO.createAccount(account);

                Account created = accountDAO.getAccountByEmail(email);

                if (created != null) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Registrierung erfolgreich");
                    response.put("accountId", created.getAccount_id());
                    response.put("surname", created.getSurname());
                    response.put("lastname", created.getLastname());
                    response.put("phonenumber", created.getPhoneNumber());
                    response.put("password", created.getPassword());
                    response.put("email", created.getEmail());
                    sendJsonResponse(response);
                } else {
                    sendErrorResponse("Registrierung fehlgeschlagen");
                }

            } catch (Exception e) {
                System.err.println("ERROR in handleRegister:");
                e.printStackTrace();
                sendErrorResponse("Registrierung fehlgeschlagen: " + e.getMessage());
            }
        }
    }