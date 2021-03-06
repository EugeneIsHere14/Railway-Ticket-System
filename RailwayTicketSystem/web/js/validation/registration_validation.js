function showError(container, errorMessage) {
    container.className = 'error';
    var msgElem = document.createElement('span');
    msgElem.style.color ='red';
    msgElem.className = "error-message";
    msgElem.innerHTML = errorMessage;
    container.appendChild(msgElem);
}

function removeErrorText(form){
    var errors = form.getElementsByClassName("error-message");
    for (var i = 0; i < errors.length; i++) {
        errors[i].remove()
    }
}

function validate(form) {

    removeErrorText(form);

    var fields = form.getElementsByClassName("field");

    var regExpLogin = /^([a-zA-Zа-яА-Я][a-zA-Zа-яА-Я0-9_\-]{2,}[a-zA-Zа-яА-Я0-9])$/;
    var regExpPass = /^([a-zA-Zа-яА-Я0-9][a-zA-Zа-яА-Я0-9_\-]{2,}[a-zA-Zа-яА-Я0-9])$/;
    var regExpConfPass = /^([a-zA-Zа-яА-Я0-9][a-zA-Zа-яА-Я0-9_\-]{2,}[a-zA-Zа-яА-Я0-9])$/;
    var regExpFName = /^([A-ZА-Я][a-zа-я]+[A-ZА-Я\-]*[a-zа-я]+)$/;
    var regExpLName = /^([A-ZА-Я][a-zа-я]+[A-ZА-Я\-]*[a-zа-я]+)$/;

    var regExps = [regExpLogin, regExpPass, regExpConfPass, regExpFName, regExpLName];

    for (var i = 0; i < fields.length; i++) {
        if (!fields[i].value) {
            showError(fields[i].parentNode, ' Enter the ' + fields[i].name + '!');
            return false;
        }

        if (fields[i].name == 'Password Confirmation' && fields[i].value &&
            fields[i].value != fields[i - 1].value) {
            showError(fields[i].parentNode, ' Passwords do not match!');
            return false;
        }

        var result = regExps[i].test(fields[i].value);

        if (fields[i].value && !result) {
            showError(fields[i].parentNode, ' ' + fields[i].name +' does not match the pattern!');
            return false;
        }
    }
    form.submit();
    return true;
}

// function resetError(container) {
//     container.className = '';
//     if (container.lastChild.className == "error-message") {
//         container.removeChild(container.lastChild);
//     }
// }

// function validate(form) {
//     var elems = form.elements;
//
//     resetError(elems.login.parentNode);
//     if (!elems.login.value) {
//         showError(elems.login.parentNode, ' Enter the login!');
//         return false;
//     }
//
//     resetError(elems.password.parentNode);
//     resetError(elems.password2.parentNode);
//     if (!elems.password.value) {
//         showError(elems.password.parentNode, ' Enter the password!');
//         return false;
//     } else if (!elems.password2.value) {
//         showError(elems.password2.parentNode, ' Enter the password confirmation!');
//         return false;
//     } else if (elems.password.value != elems.password2.value) {
//         showError(elems.password.parentNode, ' Passwords do not match!');
//         return false;
//     }
//
//     resetError(elems.firstName.parentNode);
//     if (!elems.firstName.value) {
//         showError(elems.firstName.parentNode, ' Enter the first name!');
//         return false;
//     }
//
//     resetError(elems.lastName.parentNode);
//     if (!elems.lastName.value) {
//         showError(elems.lastName.parentNode, ' Enter the last name!');
//         return false;
//     }
//
//     form.submit();
//     return true;
// }
//
// function validateRegExp(form) {
//     var elems = form.elements;
//     var loginRegExp = /^([\S]+[a-zA-Z]+[\s]?[a-zA-Z]+)/;
//     var loginResult = loginRegExp.test(elems.login.value);
//     if (elems.login.value && !loginResult) {
//         showError(elems.login.parentNode, ' Login does not match!');
//         return false;
//     } else {
//         return true;
//     }
// }