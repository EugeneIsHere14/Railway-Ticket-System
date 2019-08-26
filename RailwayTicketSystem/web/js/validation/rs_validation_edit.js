function validateEditRouteStation(form) {

    removeErrorText(form);

    var fields = form.getElementsByClassName("field");

    var regExpDate = /^((2018)\-(11|12)\-(0[1-9]|1[0-9]|2[0-9]|3[0-1])\s([0-1]\d|2[0-3])(:[0-5]\d){2}(.?[0-9]?)*)$/;
    var regExpWaitTime = /^([1-9][1-9]?[1-9]?)$/;

    var regExps = [regExpDate, regExpWaitTime, regExpDate];

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