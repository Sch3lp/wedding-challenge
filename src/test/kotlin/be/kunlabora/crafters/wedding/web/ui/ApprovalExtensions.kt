import org.approvaltests.Approvals

fun String.verify() : Unit {
    Approvals.verify(this)
}