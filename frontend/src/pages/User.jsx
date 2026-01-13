import React, { useState, useEffect } from 'react';
import keycloak from '../auth/keycloak';

const User = () => {
    const [data, setData] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch('http://localhost:8081/api/user', {
            headers: {
                Authorization: `Bearer ${keycloak.token}`,
            },
        })
            .then(async (res) => {
                if (!res.ok) throw new Error(res.statusText + ' (' + res.status + ')');
                return res.json();
            })
            .then((data) => setData(data))
            .catch((err) => setError(err.message));
    }, []);

    return (
        <div className="p-4">
            <h2 className="text-xl font-bold mb-2">User Page</h2>
            {error && <div className="text-red-500">Error: {error}</div>}
            {data && (
                <pre className="bg-gray-100 p-2 rounded">{JSON.stringify(data, null, 2)}</pre>
            )}
        </div>
    );
};

export default User;
