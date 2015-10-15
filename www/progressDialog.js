var ProgressDialog = {
    show: function () {
        cordova.exec(null, null, "ProgressDialog", "show", []);
    },
    hide: function () {
        cordova.exec(null, null, "ProgressDialog", "hide", []);
    }
};

module.exports = ProgressDialog;