$(document).ready(function () {
    $('#accountForum').formValidation({
    framework: 'bootstrap',
            icon: {
            valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
            },
            fields: {

            password: {
            validators: {
            notEmpty: {
            message: 'The password is required'
            },
                    callback: {
                    callback: function(value, validator, $field) {
                    var password = $field.val();
                            if (password == '') {
                    return true;
                    }

                    var result = zxcvbn(password),
                            score = result.score,
                            message = result.feedback.warning || 'The password is weak';
                            // Update the progress bar width and add alert class
                            var $bar = $('#strengthBar');
                            switch (score) {
                    case 0:
                            $bar.attr('class', 'progress-bar progress-bar-danger')
                            .css('width', '1%');
                            break;
                            case 1:
                            $bar.attr('class', 'progress-bar progress-bar-danger')
                            .css('width', '25%');
                            break;
                            case 2:
                            $bar.attr('class', 'progress-bar progress-bar-danger')
                            .css('width', '50%');
                            break;
                            case 3:
                            $bar.attr('class', 'progress-bar progress-bar-warning')
                            .css('width', '75%');
                            break;
                            case 4:
                            $bar.attr('class', 'progress-bar progress-bar-success')
                            .css('width', '100%');
                            break;
                    }

                    // We will treat the password as an invalid one if the score is less than 3
                    if (score < 3) {
                    return {
                    valid: false,
                            message: message
                    }
                    }

                    return true;
                    }
                    }
            }
            }
            }
    });
});

