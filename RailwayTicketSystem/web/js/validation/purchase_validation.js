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

function validatePurchase(form) {

    removeErrorText(form);

    var fields = form.getElementsByClassName("field");

    var regExpQuantity = /^\d{1,2}$/;
    var regExpCardNum = /^[0-9]{4}\s[0-9]{4}\s[0-9]{4}\s[0-9]{4}$/;
    var regExpDateExpire = /^(((0[1-9])|(1[0-2]))[/][0-9]{2})$/;
    var regExpCVV = /^([0-9]{3})$/;

    var regExps = [regExpQuantity, regExpCardNum, regExpDateExpire, regExpCVV];

    for (var i = 0; i < fields.length; i++) {
        if (!fields[i].value) {
            showError(fields[i].parentNode, ' Enter the ' + fields[i].name + '!');
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