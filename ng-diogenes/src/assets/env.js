(function (window) {
    window["env"] = window["env"] || {};

    // Environment variables
    window["env"]["production"] = false;
    window["env"]["diogenesBackendURL"] = "http://localhost:8080/diogenes";
    window["env"]["debug"] = true;
})(this);