package ntnu.katarzsz.oving4

class Game constructor (private var title: String, private var picturePath: Int, private var description: String){
    override fun toString(): String {
        return title
    }
    fun getTitle() : String{
        return title
    }
    fun getPicturePath() : Int {
        return picturePath
    }
    fun getDescription() : String {
        return description
    }
}