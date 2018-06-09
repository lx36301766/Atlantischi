package pl.atlantischi.mapadapter

import android.app.Activity
import android.content.Context
import android.view.Window
import java.io.ObjectInput
import java.io.ObjectInputStream

data class Tets(val a:Int) : User  {
    override val email: String
        get() = ""

    override val name: String
        get() = super.name

    var get:Int = 1


}

interface User {


    val email : String

    val name : String
        get() = email.substring(1)

}

interface User2 {

    val email : String

}


fun vooo() {
    var ff = Tets(3)
}

class Test2(var tets: Tets): User by tets

class ContextWrapper(activity: Activity) : Window.Callback by activity


class Con<T>(val innerSet: MutableCollection<T> = HashSet())