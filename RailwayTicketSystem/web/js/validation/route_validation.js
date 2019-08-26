function validateRoute(form) {

    removeErrorText(form);

    var fields = form.getElementsByClassName("field");

    var regExpDate = /^((2018)\-(11|12)\-(0[1-9]|1[0-9]|2[0-9]|3[0-1])\s([0-1]\d|2[0-3])(:[0-5]\d){2}(.?[0-9]?)*)$/;
    var regExpDate1 = /^(19|20)\d\d-((0[1-9]|1[012])-(0[1-9]|[12]\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)$/;

    for (var i = 0; i < fields.length; i++) {
        if (!fields[i].value) {
            showError(fields[i].parentNode, ' Enter the ' + fields[i].name + '!');
            return false;
        }

        var result = regExpDate.test(fields[i].value);

        if (fields[i].value && !result) {
            showError(fields[i].parentNode, ' ' + fields[i].name +' does not match the pattern!');
            return false;
        }
    }
    form.submit();
    return true;
}