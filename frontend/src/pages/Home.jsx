import React from 'react';
import keycloak from '../auth/keycloak';

const Home = () => {
    return (
        <div className="p-4">
            <h1 className="text-2xl font-bold mb-4">Keycloak RBAC POC</h1>
            <div className="mb-4">
                {keycloak.authenticated ? (
                    <div>
                        <p className="mb-2">Welcome, <strong>{keycloak.tokenParsed?.preferred_username}</strong></p>
                        <p className="mb-2">Roles: {keycloak.realmAccess?.roles?.join(', ')}</p>
                        <button
                            onClick={() => keycloak.logout()}
                            className="bg-red-500 text-white px-4 py-2 rounded"
                        >
                            Logout
                        </button>
                    </div>
                ) : (
                    <button
                        onClick={() => keycloak.login()}
                        className="bg-blue-500 text-white px-4 py-2 rounded"
                    >
                        Login
                    </button>
                )}
            </div>
        </div>
    );
};

export default Home;
