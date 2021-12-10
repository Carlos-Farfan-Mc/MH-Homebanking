const app = Vue.createApp({
    
    created(){
        this.loadData();
    },

    data(){
        return{
            clients: [],
            formElements: ["","",""],//<=
            json:""
        }
    },

    methods:{
        loadData(){
            axios.get('/clients')
            .then(resp => {
                this.clients = resp.data._embedded.clients;
                this.json = resp.data;
            })
        },

        addClient(){
            let client = {
                firstName : this.formElements[0],
                lastName : this.formElements[1],
                email : this.formElements[2],
            }
            this.postClient(client);
        },

        postClient(client){
            axios.post('/clients', client)
            .then(resp => {
                this.formElements.forEach(element => {
                    element = "";
                });
                this.loadData();
            })
            .catch(e => {console.log(e)})
        }
    },

    computed:{
    },
})
app.mount('#app');