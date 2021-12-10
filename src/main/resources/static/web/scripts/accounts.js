const app = Vue.createApp({

    created(){
        this.loadData();
    },

    data(){
        return{
            client: {},
            accounts: [],
            clientLoans: [],
            number:'',
            creationDate:'',
            balance:'',
        }
    },

    methods:{
        loadData(){
            axios.get('/api/clients/current')
            .then(resp => {
                this.client = resp.data;
                this.accounts = this.sortById(resp.data.accounts);
                this.clientLoans = resp.data.clientLoans;
            })
        },
        sortById(accountArray){
            accountArray.sort((accountA, accountB) => {
                if (accountA.id < accountB.id) {
                    return -1
                }
                if (accountA.id > accountB.id) {
                    return 1
                }
                return 0
            })
            return accountArray
        },
        logOut() {
            axios.post('/api/logout')
            .then(response => window.location.replace("/web/index.html"))
            .catch(e => {
                console.log(e)
            })
        },
        newAccount() {
            axios.post('/api/clients/current/accounts',"number=" + this.number + "&creationDate=" + this.creationDate + "&balance=" + this.balance,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => window.location.href="/web/accounts.html")
            .catch(e => {
                if (e.response.status === 403)
                {
                    alert(`Usted ya posee las tres cuentas permitidas`);
                }
            })
        },
        moment: function (date) {
            return moment(date);
        },
    }
})
app.mount("#app")