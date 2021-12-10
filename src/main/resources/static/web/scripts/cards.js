const app = Vue.createApp({

    created(){
        this.loadData();
    },

    data(){
        return{
            client: {},
            creditCards: [],
            debitCards: [],
        }
    },

    methods:{
        loadData(){
            axios.get('/api/clients/current')
            .then(resp => {
                this.client = resp.data;
                this.creditCards = resp.data.cards.filter(c => c.type == "CREDIT");
                this.debitCards = resp.data.cards.filter(c => c.type == "DEBIT")
            })
        },
        sortByType(cardArray){
            cardArray.sort((cardA, cardB) => {
                if (cardA.type < cardB.type) {
                    return -1
                }
                if (cardA.type > cardB.type) {
                    return 1
                }
                return 0
            })
            return cardArray
        },
        logOut() {
            axios.post('/api/logout')
            .then(response => window.location.replace("/web/index.html"))
            .catch(e => {
                console.log(e)
            })
        },
        moment: function (date) {
            return moment(date);
        },
    }
})
app.mount("#app")