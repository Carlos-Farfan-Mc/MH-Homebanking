const app = Vue.createApp({

    created(){
        this.loadLoanData();
        this.loadClientData();
    },

    data(){
        return{
            paymentAmount:'',
            loanId:'',
            amount:'',
            accountNumber:'',
            payment:'',

            loans:[],
            clients:{},
            accounts: [],
            payments:[],

        }
    },

    methods:{
        calculateAmount(){
            // debugger
            let percentage = this.loans.filter(loan => loan.id == this.loanId)[0].percentage;
            this.paymentAmount = (this.amount/this.payment*(1+percentage/100)).toFixed(2)
        },
        requestLoan(){
            axios.post('/api/loans',{id:this.loanId, amount:this.amount, payment:this.payment, accountNumber:this.accountNumber})
            .then(response => window.location.href="/web/loan-application.html")
        },
        loadPayments(){
            // debugger
            this.payments = this.loans.filter(loan => loan.id == this.loanId)[0].payments;
            this.payment = '';
            this.paymentAmount = '';
        },
        loadLoanData(){
            axios.get('/api/loans')
            .then(resp => {
                this.loans = resp.data;
            })
        },
        loadClientData(){
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
