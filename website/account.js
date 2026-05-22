
//Die fehler ausgabe also sehr wichtig um zu verstehen was passiert
function showMessage(text, type) {
    const msg = document.getElementById('message');
    msg.textContent = text;
    msg.className = 'message ' + type;
}
function showSuccess() {
    document.getElementById('formContainer').style.display = 'none';
    document.getElementById('successPage').style.display = 'block';
}



// LOGIN FORM
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch('/cgi-bin/serialisation.bat/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                action: 'login',
                email: email,
                password: password
            })
        });

        const data = await response.json();
        console.log("Response:", data);

        if (data.success) {
            document.getElementById('displayAccountId').textContent = data.accountId;
            document.getElementById('displaySurname').textContent = data.surname;
            document.getElementById('displayLastname').textContent = data.lastname;
            document.getElementById('displayPhone').textContent = data.phonenumber;
            document.getElementById('displayPassword').textContent = data.password;
            document.getElementById('displayEmail').textContent = data.email;
            showSuccess();
        } else {
            showMessage(data.error || 'Login fehlgeschlagen', 'error');
        }
    } catch (error) {
        showMessage('Verbindungsfehler: ' + error.message, 'error');
    }
});

// REGISTER FORM
document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const surname = document.getElementById('registerSurname').value;
    const lastname = document.getElementById('registerLastname').value;
    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;
    const phonenumber = document.getElementById('registerPhone').value;

    try {
        const response = await fetch('/cgi-bin/serialisation.bat/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                action: 'register',
                surname: surname,
                lastname: lastname,
                email: email,
                password: password,
                phonenumber: phonenumber
            })
        });

        const data = await response.json();
        console.log("Response:", data);

        if (data.success) {
            document.getElementById('displayAccountId').textContent = data.accountId;
            document.getElementById('displaySurname').textContent = data.surname;
            document.getElementById('displayLastname').textContent = data.lastname;
            document.getElementById('displayPhone').textContent = data.phonenumber;
            document.getElementById('displayPassword').textContent = data.password;
            document.getElementById('displayEmail').textContent = data.email;
            showSuccess();
        } else {
            showMessage(data.error || 'Registrierung fehlgeschlagen', 'error');
        }
    } catch (error) {
        showMessage('Verbindungsfehler: ' + error.message, 'error');
    }
});