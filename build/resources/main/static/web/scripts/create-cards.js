const app = Vue.createApp({

    data(){
        return{
            type:"DEBIT",
            color:"SILVER",
        }
    },
    methods:{
        logOut() {
            axios.post('/api/logout')
            .then(response => window.location.replace("/web/index.html"))
            .catch(e => {
                console.log(e)
            })
        },
        crearTarjeta() {
            axios.post('/api/clients/current/cards',"type=" + this.type + "&color=" + this.color,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => window.location.href="/web/cards.html")
            .catch(e => {
                if (e.response.status === 403)
                {
                    alert(`Usted ya posee tres tarjetas del tipo seleccionado (${this.type == 'DEBIT' ? 'Debito' : 'Credito'}).`);
                }
            })
        },
    }
})
app.mount("#app")