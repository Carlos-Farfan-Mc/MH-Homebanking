const app = Vue.createApp({

    created(){
        this.loadData();
    },

    data(){
        return{
            account: [],
            transactions: [],
        }
    },

    methods:{
        loadData(){
            const urlParams = new URLSearchParams(window.location.search);
            const myParam = urlParams.get('id');
            axios.get(`/api/accounts/${myParam}`)
            .then(resp => {
                this.account = resp.data;
                this.transactions = this.sortByDate(resp.data.transactions);
            })
        },
        sortByDate(accountArray){
            accountArray.sort((accountA, accountB) => {
                var fechaA = new Date(accountA.date)
                var fechaB = new Date(accountB.date)

                if (fechaA > fechaB) {
                    return -1
                }
                if (fechaA < fechaB) {
                    return 1
                }
                return 0
            })
            return accountArray
        },
        logOut: function() {
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