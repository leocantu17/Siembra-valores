

const cmsSesiones = {
    login: async (req, res) => {
        try {
            res.render('inicio-sesion')
        } catch (error) {
            console.log(error)
        }
    },
    logout: async (req, res) => {
        try {
            req.session.destroy()
            res.redirect('/')
        } catch (error) {
            console.log(error)
        }
    },
}

module.exports = cmsSesiones