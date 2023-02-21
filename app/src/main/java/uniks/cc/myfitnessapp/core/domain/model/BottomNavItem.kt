package uniks.cc.myfitnessapp.core.domain.model

data class BottomNavItem(
    val title : String,
    val route : String,
    val IconId : Int,
    var isSelected : Boolean
)
