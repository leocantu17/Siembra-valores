const sqlConfig = {
    user: 'sa',
    password: 'sasa',
    database: 'SIEMBRA_VALORES',
    server: 'localhost',
    port: 1433,
    pool: {
        max: 10,
        min: 0,
        idleTimeoutMillis: 30000
    },
    options: {
        encrypt: false, // for azure
        trustServerCertificate: false // change to true for local dev / self-signed certs
    }
}


module.exports = sqlConfig