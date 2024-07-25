fun main() {
    var chequingAccount1 = ChequingAccount("1111111",
        "C1001", 200.00, 100.00)

    chequingAccount1.deposit(50.00)
    chequingAccount1.withdraw(100.00)

    chequingAccount1.withdraw(500.00)
    chequingAccount1.deposit(50.00)

    println(chequingAccount1.toString())

    var chequingAccount2 = ChequingAccount("1111112",
        "C1002")
    println(chequingAccount2.toString())

    var chequingAccount3 = ChequingAccount("1111113",
        "C1003", 500.00)
    println(chequingAccount3.toString())

    var savingsAccount1 = SavingsAccount("1111114",
        "C1003", 500.00, false)
    println(savingsAccount1.toString())
    savingsAccount1.deposit(500.00)
    savingsAccount1.payBill(100.00)
    savingsAccount1.withdraw(200.00)
    println(savingsAccount1.toString())
    savingsAccount1.taxFreeStatus = true
    println(savingsAccount1.toString())

    // Testing JointChequingAccount
    var jointChequingAccount = JointChequingAccount("1111115", "C1004", "C1005", 300.00, 200.00)
    println(jointChequingAccount.toString())
    jointChequingAccount.deposit(100.00)
    jointChequingAccount.withdraw(150.00)
    println(jointChequingAccount.toString())

    // Testing RRSPAccount
    var rrspAccount = RRSPAccount("1111116", "C1006", 1000.00, 5, 0.05)
    println(rrspAccount.toString())
    rrspAccount.deposit(500.00)
    rrspAccount.withdraw(200.00)
    println(rrspAccount.toString())
}

abstract class BankAccount(
    val accountNumber: String,
    val clientNumber: String,
    var balance: Double = 0.0
) {
    open fun deposit(amount: Double) {
        balance += amount
        println("After a deposit of $amount the new balance is $balance")
    }

    open fun withdraw(amount: Double) {
        if (amount <= balance) {
            balance -= amount
            println("After a withdrawal of $amount the new balance is $balance")
        } else {
            println("Withdrawal amount of $amount exceeds available funds.")
        }
    }

    override fun toString(): String {
        return "Account #: $accountNumber  Client #: $clientNumber  Balance: $%.2f".format(balance)
    }
}

open class ChequingAccount(
    accountNumber: String,
    clientNumber: String,
    balance: Double = 0.0,
    var overdraftLimit: Double = 0.0
) : BankAccount(accountNumber, clientNumber, balance) {
    override fun withdraw(amount: Double) {
        if (amount <= balance + overdraftLimit) {
            balance -= amount
            println("After a withdrawal of $amount the new balance is $balance")
        } else {
            println("Withdrawal amount of $amount exceeds available funds.")
        }
    }

    open fun payBill(amount: Double) {
        if (amount <= balance + overdraftLimit) {
            balance -= amount
            println("After a bill payment of $amount the new balance is $balance")
        } else {
            println("Bill payment amount of $amount exceeds available funds.")
        }
    }

    override fun toString(): String {
        return super.toString() + " Overdraft Limit: $%.2f".format(overdraftLimit)
    }
}

open class SavingsAccount(
    accountNumber: String,
    clientNumber: String,
    balance: Double = 0.0,
    var taxFreeStatus: Boolean = false
) : BankAccount(accountNumber, clientNumber, balance) {
    open fun payBill(amount: Double) {
        if (amount <= balance) {
            balance -= amount
            println("After a bill payment of $amount the new balance is $balance")
        } else {
            println("Bill payment amount of $amount exceeds available funds.")
        }
    }

    override fun toString(): String {
        val status = if (taxFreeStatus) "  Tax Free: active" else "  Tax Free: inactive"
        return super.toString() + status
    }
}

// New JointChequingAccount class
class JointChequingAccount(
    accountNumber: String,
    clientNumber: String,
    val secondClientNumber: String,
    balance: Double = 0.0,
    overdraftLimit: Double = 0.0
) : ChequingAccount(accountNumber, clientNumber, balance, overdraftLimit) {
    override fun toString(): String {
        return "Account #: $accountNumber  Client 1 #: $clientNumber  Client 2 #: $secondClientNumber  Balance: $%.2f Overdraft Limit: $%.2f".format(balance, overdraftLimit)
    }
}

// New RRSPAccount class
class RRSPAccount(
    accountNumber: String,
    clientNumber: String,
    balance: Double = 0.0,
    val term: Int,
    val interestRate: Double
) : BankAccount(accountNumber, clientNumber, balance) {
    override fun toString(): String {
        return "Account #: $accountNumber  Client #: $clientNumber  Balance: $%.2f  Term: $term years  Interest Rate: %.2f%%".format(balance, interestRate * 100)
    }
}
