(function (window) {
    window["env"] = window["env"] || {};

    // Environment variables
    window["env"]["production"] = "${PRODUCTION}";
    window["env"]["diogenesBackendURL"] = "${DIOGENES_BACKEND_URL}";
    window["env"]["debug"] = "${DEBUG}";
})(this);