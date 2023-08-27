// Define an interface for the window object with the 'env' property
interface WindowWithEnv {
    env: {
        production: boolean
        diogenesBackendURL?: string;
        debug?: boolean;
    };
}

// Extend the global Window interface with the custom window object
declare global {
    interface Window extends WindowWithEnv { }
}

export const environment = {
    production: window.env.production ?? false,
    diogenesBackendURL: window.env.diogenesBackendURL ?? "http://localhost:8080/diogenes",
    debug: window.env.debug ?? false,
};