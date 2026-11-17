import com.group4.group4androidcapstone.features.products.domain.Product
import com.group4.group4androidcapstone.features.products.domain.ProductDetails
import com.group4.group4androidcapstone.features.products.domain.ProductRepository
import com.group4.group4androidcapstone.testutil.builders.aProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime

class FakeProductRepository : ProductRepository {

    var products = mutableListOf(
        aProduct(productId = 1, name = "Pear"),
        aProduct(productId = 2, name = "Apple"),
        aProduct(productId = 3, name = "Orange"),
        aProduct(productId = 4, name = "Grape")
    )

    override fun observeProducts(): Flow<List<Product>> {
        return flowOf(products)
    }

    override fun observeRecentProducts(): Flow<List<Product>> {
        return flowOf(products.sortedBy { it.viewedTime })

    }

    override fun observeProductDetails(id: Int): Flow<ProductDetails?> {
        return emptyFlow<ProductDetails>()
    }

    override suspend fun refreshProducts() {
        return Unit
    }

    override suspend fun refreshProductDetails(id: Int) {
        return Unit
    }

    override suspend fun setProductViewedTime(
        id: Int,
        viewedTime: LocalDateTime?
    ) {
        return Unit
    }

    override suspend fun clearProductViewedTimes() {
        return Unit
    }

}