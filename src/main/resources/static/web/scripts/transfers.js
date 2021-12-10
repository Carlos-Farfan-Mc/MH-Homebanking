const app = Vue.createApp({

    created(){
        this.loadData();
    },

    data(){
        return{
            sourceAccount:'',
            targetAccount:'',
            targetType:'propia',
            amount:'',
            description:'',

            accounts: [],
            targetAccounts:[],
            clientLoans: [],
            number:'',
            creationDate:'',
            balance:'',
        }
    },

    methods:{
        transferAmount() {
            axios.post('/api/transactions',`amount=${this.amount}&description=${this.description}&sourceNumber=${this.sourceAccount}&targetNumber=${this.targetAccount}`,
            {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(response => window.location.href="/web/transfers.html")
            // .catch(e => console.log(e))
        },
        filterTargetAccounts: function () {
            this.targetAccounts = this.accounts.filter(account => account.number != this.sourceAccount);
            this.targetAccount = ''
        },
        selectTarget: function (target){
            this.targetType = target;
        },
        loadData(){
            axios.get('/api/clients/current')
            .then(resp => {
                this.client = resp.data;
                this.accounts = this.sortById(resp.data.accounts);
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
    }
})
app.mount("#app")
