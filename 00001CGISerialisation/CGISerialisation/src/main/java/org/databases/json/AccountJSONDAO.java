package org.databases.json;

import org.interfaces.IAccount;
import org.databases.entity.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountJSONDAO implements IAccount {

    private static final String FILE_PATH = "data/json/accounts.json";
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createAccount(Account account) {
        try {
            List<Account> accounts = loadAll();

            // Auto-increment ID
            int newId = accounts.isEmpty() ? 1 :
                    accounts.stream()
                            .mapToInt(Account::getAccount_id)
                            .max().getAsInt() + 1;

            account.setAccount_id(newId);
            accounts.add(account);

            saveAll(accounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account getAccountById(int accountId) {
        try {
            return loadAll().stream()
                    .filter(a -> a.getAccount_id() == accountId)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account getAccountByEmail(String email) {
        try {
            return loadAll().stream()
                    .filter(a -> a.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        try {
            return loadAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void updateAccount(Account account) {
        try {
            List<Account> accounts = loadAll();

            for (int i = 0; i < accounts.size(); i++) {
                if (accounts.get(i).getAccount_id() == account.getAccount_id()) {
                    accounts.set(i, account);
                    break;
                }
            }


            saveAll(accounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePassword(int accountId, String newPassword) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setPassword(newPassword);
            updateAccount(account);
        }
    }

    @Override
    public void updateEmail(int accountId, String newEmail) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setEmail(newEmail);
            updateAccount(account);
        }
    }

    @Override
    public void deleteAccount(int accountId) {
        try {
            List<Account> accounts = loadAll();
            accounts.removeIf(a -> a.getAccount_id() == accountId);
            saveAll(accounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account login(String email, String password) {
        try {
            Account account = getAccountByEmail(email);

            if (account != null && account.getPassword().equals(password)) {
                return account;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public boolean emailExists(String email) {
        return getAccountByEmail(email) != null;
    }

    private List<Account> loadAll() throws Exception {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveAll(new ArrayList<>());
            return new ArrayList<>();
        }

        return mapper.readValue(file, new TypeReference<List<Account>>() {});
    }

    private void saveAll(List<Account> accounts) throws Exception {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(file, accounts);
    }
}