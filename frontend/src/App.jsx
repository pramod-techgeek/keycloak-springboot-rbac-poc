import React, { useState, useEffect } from 'react';
import { BrowserRouter, Routes, Route, Link, Navigate } from 'react-router-dom';
import keycloak from './auth/keycloak';
import Home from './pages/Home';
import User from './pages/User';
import Admin from './pages/Admin';

const App = () => {
    const [initialized, setInitialized] = useState(false);

    useEffect(() => {
        keycloak.init({ onLoad: 'check-sso', checkLoginIframe: false }).then((authenticated) => {
            setInitialized(true);
        }).catch(console.error);
    }, []);

    if (!initialized) {
        return <div className="p-4">Loading Keycloak...</div>;
    }

    return (
        <BrowserRouter>
            <div className="min-h-screen bg-gray-50 text-gray-900 font-sans">
                <nav className="bg-white shadow p-4 mb-4 flex gap-4">
                    <Link to="/" className="text-blue-600 font-medium hover:underline">Home</Link>
                    {keycloak.authenticated && (
                        <>
                            <Link to="/user" className="text-blue-600 font-medium hover:underline">User Page</Link>
                            {keycloak.hasRealmRole('ADMIN') && (
                                <Link to="/admin" className="text-blue-600 font-medium hover:underline">Admin Page</Link>
                            )}
                        </>
                    )}
                </nav>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/user" element={keycloak.authenticated ? <User /> : <Navigate to="/" />} />
                    <Route path="/admin" element={keycloak.authenticated && keycloak.hasRealmRole('ADMIN') ? <Admin /> : <div className="p-4 text-red-500">Access Denied</div>} />
                </Routes>
            </div>
        </BrowserRouter>
    );
};

export default App;
