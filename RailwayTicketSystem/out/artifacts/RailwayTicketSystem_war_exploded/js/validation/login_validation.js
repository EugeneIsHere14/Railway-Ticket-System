function validateLogin(form) {

    removeErrorText(form);

    var fields = form.getElementsByClassName("field");

    var regExpLogin = /^([a-zA-Zа-яА-Я][a-zA-Zа-яА-Я0-9_\-]{2,}[a-zA-Zа-яА-Я0-9])$/;
    var regExpPass = /^([a-zA-Zа-яА-Я0-9][a-zA-Zа-яА-Я0-9_\-]{2,}[a-zA-Zа-яА-Я0-9])$/;

    var regExps = [regExpLogin, regExpPass];

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