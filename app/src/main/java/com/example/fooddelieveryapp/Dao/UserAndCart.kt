import androidx.room.Embedded
import androidx.room.Relation
import com.example.fooddelieveryapp.Dao.Cart
import com.example.fooddelieveryapp.Dao.User


data class UserAndCart(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "ownerId"
    )
    val cart: Cart
)
