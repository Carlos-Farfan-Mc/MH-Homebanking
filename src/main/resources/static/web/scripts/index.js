const app = Vue.createApp({

    created(){
        
    },

    data(){
        return{
            usuario: "registrado",
            formNuevo: false,
            formRegistrado: true,
            firstName:'',
            lastName:'',
            email:'',
            password:'',
        }
    },

    methods:{
        login() {
            axios.post('/api/login',`email=${this.email}&password=${this.password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => window.location.href="/web/accounts.html")
            .catch(e => {
                // debugger;
                if (e.response.status === 401)
                {
                    alert('Información de inicio de sesión no válida.');
                }
                else
                {
                    alert('Houston, we have a problem...');
                }
                // console.log(e)
            })
        },
        newClient() {
            axios.post('/api/clients',"firstName=" + this.firstName + "&lastName=" + this.lastName + "&email=" + this.email + "&password=" + this.password,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => 
                axios.post('/api/login',"email=" + this.email + "&password=" + this.password,
                {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => 
                    axios.post('/api/clients/current/accounts',"number=" + this.number + "&creationDate=" + this.creationDate + "&balance=" + this.balance,
                    {headers:{'content-type':'application/x-www-form-urlencoded'}})
                    .then(response => window.location.href="/web/accounts.html")))
        },
    }
})
app.mount("#app")
