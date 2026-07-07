package pe.universal.unidulza

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnidulzaTheme {
                UnidulzaApp()
            }
        }
    }
}

private val Cream = Color(0xFFFFF6EC)
private val Cocoa = Color(0xFF211142)
private val Purple = Color(0xFF5425DB)
private val DeepPurple = Color(0xFF2D1478)
private val Coral = Color(0xFFFF6048)
private val Rose = Color(0xFFFF4F86)
private val Honey = Color(0xFFFFC928)
private val Mint = Color(0xFF00A884)
private val Sky = Color(0xFF47C7FF)
private val Lilac = Color(0xFFE4DAFF)
private val Lavender = Color(0xFFC9B8FF)
private val InkMuted = Color(0xFF736982)

data class Product(
    val sku: String,
    val name: String,
    val family: String,
    val price: Double,
    val oldPrice: Double,
    val discountPercent: Int,
    val unit: String,
    val unitsPerBox: Int,
    val stockBoxes: Int,
    val accent: Color,
    val shape: PackShape
)

data class Category(
    val name: String,
    val label: String,
    val icon: ImageVector,
    val accent: Color,
    val products: String
)

enum class PackShape { Pouch, Box, Bottle, Bowl }

data class Order(
    val code: String,
    val total: Double,
    val boxes: Int,
    val status: String = "Pedido en camino"
)

private val categories = listOf(
    Category("Gelatinas", "Sabores top", Icons.Default.Cake, Rose, "32 skus"),
    Category("Flanes", "Alta rotacion", Icons.Default.Star, Honey, "18 skus"),
    Category("Reposteria", "Insumos clave", Icons.Default.Inventory2, Purple, "44 skus"),
    Category("Lanzamientos", "Novedades", Icons.Default.Storefront, Sky, "9 skus")
)

private val products = listOf(
    Product("UNI-GEL-FRE-150", "Gelatina Universal fresa 150 g", "Postres", 2.80, 3.10, 10, "24 ud es 1 caja", 24, 86, Rose, PackShape.Pouch),
    Product("UNI-MAZ-MOR-160", "Mazamorra morada Universal 160 g", "Postres", 3.40, 3.80, 11, "24 ud es 1 caja", 24, 64, Purple, PackShape.Box),
    Product("UNI-FLA-VAI-100", "Flan vainilla Universal 100 g", "Flanes", 2.95, 3.20, 8, "36 ud es 1 caja", 36, 42, Honey, PackShape.Bowl),
    Product("UNI-COB-CHO-500", "Cobertura sabor chocolate 500 g", "Reposteria", 8.90, 9.80, 9, "12 ud es 1 caja", 12, 28, Color(0xFF8B4E2F), PackShape.Bottle),
    Product("UNI-GEL-PIA-150", "Gelatina Universal pina 150 g", "Postres", 2.75, 3.05, 10, "24 ud es 1 caja", 24, 51, Sky, PackShape.Pouch),
    Product("UNI-DEC-CHO-250", "Decoracion sabor chocolate 250 g", "Reposteria", 6.40, 7.10, 10, "18 ud es 1 caja", 18, 19, Coral, PackShape.Bottle)
)

enum class Tab(val title: String, val icon: ImageVector) {
    Home("Inicio", Icons.Default.Home),
    Promos("Promos", Icons.Default.Discount),
    Cart("Carrito", Icons.Default.ShoppingCart),
    Orders("Pedidos", Icons.Default.LocalShipping),
    Business("Negocio", Icons.Default.Person)
}

private fun money(value: Double) = "S/ ${"%.2f".format(value)}"

@Composable
fun UnidulzaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = androidx.compose.material3.lightColorScheme(
            primary = Purple,
            secondary = Rose,
            tertiary = Honey,
            background = Cream,
            surface = Color.White,
            onPrimary = Color.White,
            onSurface = Cocoa
        ),
        content = content
    )
}

@Composable
fun UnidulzaApp() {
    var selectedTab by remember { mutableStateOf(Tab.Home) }
    val cart = remember { mutableStateMapOf<String, Int>() }
    val orders = remember { mutableStateListOf<Order>() }
    val cartItems = cart.values.sum()
    val subtotal = products.sumOf { product -> product.price * product.unitsPerBox * (cart[product.sku] ?: 0) }
    val oldSubtotal = products.sumOf { product -> product.oldPrice * product.unitsPerBox * (cart[product.sku] ?: 0) }
    val savings = (oldSubtotal - subtotal).coerceAtLeast(0.0)
    val minimumOrder = 120.0

    fun addProduct(product: Product) {
        val current = cart[product.sku] ?: 0
        if (current < product.stockBoxes) {
            cart[product.sku] = current + 1
        }
    }

    fun removeProduct(product: Product) {
        val current = cart[product.sku] ?: 0
        if (current <= 1) {
            cart.remove(product.sku)
        } else {
            cart[product.sku] = current - 1
        }
    }

    fun placeOrder() {
        if (cart.isNotEmpty() && subtotal >= minimumOrder) {
            val code = "UN-${2486 + orders.size}"
            orders.add(0, Order(code = code, total = subtotal, boxes = cartItems))
            cart.clear()
            selectedTab = Tab.Orders
        }
    }

    Scaffold(
        containerColor = Cream,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = {
                            if (tab == Tab.Cart && cartItems > 0) {
                                BadgedBox(badge = { Badge(containerColor = Coral) { Text(cartItems.toString()) } }) {
                                    Icon(tab.icon, contentDescription = tab.title)
                                }
                            } else {
                                Icon(tab.icon, contentDescription = tab.title)
                            }
                        },
                        label = { Text(tab.title, fontSize = 11.sp) }
                    )
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            Tab.Home -> HomeScreen(padding, cartItems, subtotal, savings, onAdd = ::addProduct, onOpenCart = { selectedTab = Tab.Cart })
            Tab.Promos -> PromoScreen(padding, onAdd = ::addProduct)
            Tab.Cart -> CartScreen(
                padding = padding,
                cart = cart,
                subtotal = subtotal,
                oldSubtotal = oldSubtotal,
                savings = savings,
                minimumOrder = minimumOrder,
                onAdd = ::addProduct,
                onRemove = ::removeProduct,
                onPlaceOrder = ::placeOrder
            )
            Tab.Orders -> OrdersScreen(padding, orders.firstOrNull(), orders)
            Tab.Business -> BusinessScreen(padding, cartItems, savings, orders)
        }
    }
}

@Composable
fun HomeScreen(
    padding: PaddingValues,
    cartItems: Int,
    subtotal: Double,
    savings: Double,
    onAdd: (Product) -> Unit,
    onOpenCart: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(padding),
        contentPadding = PaddingValues(bottom = 22.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item { ImpactHero(cartItems, subtotal, onOpenCart) }
        item {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricCard("Pedido minimo", "S/ 120", "Activa despacho", Purple, Modifier.weight(1f))
                MetricCard("Ahorro ahora", money(savings), "En carrito", Mint, Modifier.weight(1f))
            }
        }
        item {
            SectionTitle("Nuevas categorias", "Elige rapido lo que mas vendes")
            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories) { CategoryShowcase(it) }
            }
        }
        item {
            CampaignBanner()
        }
        item {
            SectionTitle("Productos estrella", "Listos para agregar por caja")
        }
        item {
            ProductGrid(onAdd)
        }
    }
}

@Composable
fun ImpactHero(cartItems: Int, subtotal: Double, onOpenCart: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(390.dp)
            .background(Brush.verticalGradient(listOf(Lilac, Cream)))
    ) {
        HeroBackground()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, top = 28.dp, end = 22.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BrandMark(46.dp)
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("Unidulza", color = Purple, fontSize = 26.sp, fontWeight = FontWeight.Black)
                    Text("Universal para distribuidores", color = DeepPurple, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.weight(1f))
                Surface(
                    color = Color.White.copy(alpha = .78f),
                    shape = CircleShape,
                    shadowElevation = 5.dp
                ) {
                    BadgedBox(
                        badge = { if (cartItems > 0) Badge(containerColor = Coral) { Text(cartItems.toString()) } },
                        modifier = Modifier.clickable(onClick = onOpenCart).padding(11.dp)
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito", tint = Purple, modifier = Modifier.size(22.dp))
                    }
                }
            }

            Text(
                "Endulza tu negocio con pedidos mas inteligentes",
                color = Cocoa,
                fontSize = 32.sp,
                lineHeight = 35.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(top = 22.dp, end = 30.dp)
            )
            Text(
                "Promos, reposicion y despacho Universal en una experiencia visual para vender mas.",
                color = InkMuted,
                fontSize = 14.sp,
                lineHeight = 19.sp,
                modifier = Modifier.padding(top = 9.dp, end = 54.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .height(178.dp)
                .clip(RoundedCornerShape(topStart = 38.dp, topEnd = 38.dp))
                .background(Brush.linearGradient(listOf(Purple, DeepPurple)))
        ) {
            BasketIllustration(Modifier.align(Alignment.BottomCenter))
            FloatingPack(
                label = "FLAN",
                color = Honey,
                modifier = Modifier.align(Alignment.TopStart).offset(x = 32.dp, y = (-22).dp),
                shape = PackShape.Bowl
            )
            FloatingPack(
                label = "GEL",
                color = Rose,
                modifier = Modifier.align(Alignment.TopCenter).offset(x = 18.dp, y = (-40).dp),
                shape = PackShape.Pouch
            )
            FloatingPack(
                label = "MORA",
                color = Purple,
                modifier = Modifier.align(Alignment.TopEnd).offset(x = (-34).dp, y = (-16).dp),
                shape = PackShape.Box
            )
            MascotBubble(Modifier.align(Alignment.BottomEnd).offset(x = (-18).dp, y = (-20).dp))
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                shadowElevation = 8.dp,
                modifier = Modifier.align(Alignment.TopStart).padding(start = 16.dp, top = 18.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Discount, contentDescription = null, tint = Mint, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Hasta 15% dsct.", color = Cocoa, fontWeight = FontWeight.Black, fontSize = 13.sp)
                }
            }
            if (cartItems > 0) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(18.dp),
                    shadowElevation = 8.dp,
                    modifier = Modifier.align(Alignment.BottomStart).padding(start = 16.dp, bottom = 18.dp).clickable(onClick = onOpenCart)
                ) {
                    Column(Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                        Text("Carrito listo", color = InkMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(money(subtotal), color = Purple, fontSize = 18.sp, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun HeroBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(Color.White.copy(alpha = .50f), radius = 170f, center = Offset(size.width * .80f, -10f))
        drawCircle(Purple.copy(alpha = .08f), radius = 125f, center = Offset(size.width * .10f, size.height * .38f))
        drawCircle(Coral.copy(alpha = .10f), radius = 95f, center = Offset(size.width * .95f, size.height * .53f))
        val wave = Path().apply {
            moveTo(0f, size.height * .66f)
            cubicTo(size.width * .30f, size.height * .55f, size.width * .62f, size.height * .78f, size.width, size.height * .62f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        drawPath(wave, Color.White.copy(alpha = .35f))
    }
}

@Composable
fun BasketIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxWidth().height(130.dp)) {
        val basket = Path().apply {
            moveTo(size.width * .10f, size.height * .42f)
            lineTo(size.width * .90f, size.height * .28f)
            lineTo(size.width * .78f, size.height * .92f)
            lineTo(size.width * .20f, size.height * .92f)
            close()
        }
        drawPath(basket, Lavender)
        drawPath(basket, Purple.copy(alpha = .18f))
        drawLine(Color.White.copy(alpha = .42f), Offset(size.width * .20f, size.height * .52f), Offset(size.width * .82f, size.height * .40f), strokeWidth = 7f)
        drawLine(Color.White.copy(alpha = .30f), Offset(size.width * .26f, size.height * .72f), Offset(size.width * .75f, size.height * .62f), strokeWidth = 7f)
    }
}

@Composable
fun FloatingPack(label: String, color: Color, shape: PackShape, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(width = 74.dp, height = 92.dp), contentAlignment = Alignment.Center) {
        ProductArt(color = color, shape = shape, modifier = Modifier.fillMaxSize())
        Text(label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun ProductArt(color: Color, shape: PackShape, modifier: Modifier = Modifier) {
    val packageShape = when (shape) {
        PackShape.Pouch -> RoundedCornerShape(14.dp, 14.dp, 20.dp, 20.dp)
        PackShape.Box -> RoundedCornerShape(9.dp)
        PackShape.Bottle -> RoundedCornerShape(24.dp, 24.dp, 12.dp, 12.dp)
        PackShape.Bowl -> RoundedCornerShape(36.dp, 36.dp, 18.dp, 18.dp)
    }
    Box(
        modifier = modifier
            .shadow(10.dp, packageShape)
            .clip(packageShape)
            .background(Brush.verticalGradient(listOf(color.copy(alpha = .86f), color)))
            .border(2.dp, Color.White.copy(alpha = .45f), packageShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(Color.White.copy(alpha = .24f), radius = size.minDimension * .38f, center = Offset(size.width * .28f, size.height * .25f))
            drawCircle(Color.White.copy(alpha = .18f), radius = size.minDimension * .30f, center = Offset(size.width * .78f, size.height * .85f))
        }
        Surface(color = Color.White.copy(alpha = .18f), shape = RoundedCornerShape(999.dp)) {
            Text("U", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
        }
    }
}

@Composable
fun MascotBubble(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(84.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(3.dp, Lilac, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(Coral, radius = 8f, center = Offset(size.width * .36f, size.height * .42f))
            drawCircle(Coral, radius = 8f, center = Offset(size.width * .63f, size.height * .42f))
            drawCircle(Coral.copy(alpha = .88f), radius = 20f, center = Offset(size.width * .50f, size.height * .64f))
        }
    }
}

@Composable
fun BrandMark(size: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(14.dp))
            .background(Brush.linearGradient(listOf(Purple, Rose))),
        contentAlignment = Alignment.Center
    ) {
        Text("u", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun MetricCard(title: String, value: String, caption: String, color: Color, modifier: Modifier) {
    Card(
        modifier = modifier.height(92.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(9.dp).clip(CircleShape).background(color))
                Spacer(Modifier.width(7.dp))
                Text(title, color = InkMuted, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text(value, color = Cocoa, fontSize = 25.sp, fontWeight = FontWeight.Black)
            Text(caption, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SectionTitle(title: String, subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            Text(title, color = Cocoa, fontSize = 23.sp, fontWeight = FontWeight.Black)
            Text(subtitle, color = InkMuted, fontSize = 13.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Ver todo", color = Purple, fontSize = 13.sp, fontWeight = FontWeight.Black)
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Purple, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun CategoryShowcase(category: Category) {
    Card(
        modifier = Modifier.width(168.dp).height(178.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Canvas(Modifier.fillMaxSize()) {
                drawCircle(category.accent.copy(alpha = .13f), radius = 125f, center = Offset(size.width * .80f, size.height * .08f))
                drawCircle(category.accent.copy(alpha = .08f), radius = 95f, center = Offset(size.width * .10f, size.height * .96f))
            }
            Column(Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier.size(56.dp).clip(CircleShape).background(category.accent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(category.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                }
                Spacer(Modifier.height(18.dp))
                Text(category.name, color = Cocoa, fontSize = 20.sp, fontWeight = FontWeight.Black)
                Text(category.label, color = InkMuted, fontSize = 13.sp)
                Spacer(Modifier.weight(1f))
                Pill(category.products, category.accent.copy(alpha = .13f), category.accent)
            }
        }
    }
}

@Composable
fun CampaignBanner() {
    Card(
        modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = DeepPurple),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(Modifier.fillMaxWidth().height(170.dp)) {
            Canvas(Modifier.fillMaxSize()) {
                drawCircle(Honey.copy(alpha = .24f), radius = 135f, center = Offset(size.width * .95f, size.height * .10f))
                drawCircle(Rose.copy(alpha = .30f), radius = 92f, center = Offset(size.width * .06f, size.height * .92f))
            }
            Column(Modifier.padding(18.dp).fillMaxWidth(.62f)) {
                Pill("Campana Universal", Honey, Cocoa)
                Text("Combo repostero para crecer tu ticket", color = Color.White, fontSize = 23.sp, lineHeight = 26.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(top = 12.dp))
                Text("Mezcla postres + cobertura y desbloquea despacho preferente.", color = Color.White.copy(alpha = .76f), fontSize = 12.sp, lineHeight = 16.sp, modifier = Modifier.padding(top = 7.dp))
            }
            ProductArt(Rose, PackShape.Pouch, Modifier.align(Alignment.BottomEnd).offset(x = (-72).dp, y = 20.dp).size(92.dp))
            ProductArt(Honey, PackShape.Bowl, Modifier.align(Alignment.CenterEnd).offset(x = (-20).dp, y = (-12).dp).size(96.dp))
        }
    }
}

@Composable
fun ProductGrid(onAdd: (Product) -> Unit) {
    Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ProductTile(products[0], onAdd, Modifier.weight(1f))
            ProductTile(products[1], onAdd, Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ProductTile(products[2], onAdd, Modifier.weight(1f))
            ProductTile(products[3], onAdd, Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ProductTile(products[4], onAdd, Modifier.weight(1f))
            ProductTile(products[5], onAdd, Modifier.weight(1f))
        }
    }
}

@Composable
fun ProductTile(product: Product, onAdd: (Product) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.heightIn(min = 246.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.12f)
                    .clip(RoundedCornerShape(22.dp))
                    .background(product.accent.copy(alpha = .12f))
            ) {
                Canvas(Modifier.fillMaxSize()) {
                    drawCircle(Color.White.copy(alpha = .76f), radius = 74f, center = Offset(size.width * .65f, size.height * .28f))
                    drawCircle(product.accent.copy(alpha = .13f), radius = 82f, center = Offset(size.width * .18f, size.height * .92f))
                }
                ProductArt(product.accent, product.shape, Modifier.align(Alignment.Center).size(width = 78.dp, height = 100.dp))
                Pill("${product.discountPercent}% dsct.", Mint, Color.White)
            }
            Text(
                product.name,
                color = Cocoa,
                fontSize = 14.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 10.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 7.dp)) {
                Text(money(product.price), color = Mint, fontSize = 20.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.width(6.dp))
                Text(money(product.oldPrice), color = InkMuted, fontSize = 12.sp, textDecoration = TextDecoration.LineThrough)
            }
            Text(product.unit, color = InkMuted, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
            Text("Stock: ${product.stockBoxes} cajas", color = product.accent, fontSize = 11.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(top = 2.dp))
            Button(
                onClick = { onAdd(product) },
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("Agregar", fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
fun PromoScreen(padding: PaddingValues, onAdd: (Product) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream).padding(padding),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Box(Modifier.fillMaxWidth().height(280.dp).background(Brush.verticalGradient(listOf(Lilac, Cream)))) {
                HeroBackground()
                Column(Modifier.padding(22.dp)) {
                    Text("Promociones", color = Cocoa, fontSize = 34.sp, fontWeight = FontWeight.Black)
                    Text("Campanas listas para mejorar tu margen.", color = InkMuted, fontSize = 15.sp)
                }
                CampaignBanner()
            }
        }
        item { SectionTitle("Descuentos activos", "Compra por caja y gana margen") }
        item { ProductGrid(onAdd) }
    }
}

@Composable
fun CartScreen(
    padding: PaddingValues,
    cart: Map<String, Int>,
    subtotal: Double,
    oldSubtotal: Double,
    savings: Double,
    minimumOrder: Double,
    onAdd: (Product) -> Unit,
    onRemove: (Product) -> Unit,
    onPlaceOrder: () -> Unit
) {
    val cartProducts = products.filter { (cart[it.sku] ?: 0) > 0 }
    val missing = (minimumOrder - subtotal).coerceAtLeast(0.0)
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream).padding(padding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Carrito", color = Cocoa, fontSize = 34.sp, fontWeight = FontWeight.Black)
            Text("Ajusta cajas y confirma tu pedido.", color = InkMuted, fontSize = 15.sp)
        }
        if (cartProducts.isEmpty()) {
            item { EmptyState("Tu carrito esta vacio", "Agrega productos desde Inicio o Promos para armar tu pedido.") }
        } else {
            items(cartProducts) { product ->
                CartItemRow(
                    product = product,
                    quantity = cart[product.sku] ?: 0,
                    onAdd = { onAdd(product) },
                    onRemove = { onRemove(product) }
                )
            }
            item {
                CartSummaryCard(
                    subtotal = subtotal,
                    oldSubtotal = oldSubtotal,
                    savings = savings,
                    missing = missing,
                    canOrder = missing <= 0.0,
                    onPlaceOrder = onPlaceOrder
                )
            }
        }
    }
}

@Composable
fun CartItemRow(product: Product, quantity: Int, onAdd: () -> Unit, onRemove: () -> Unit) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(82.dp).clip(RoundedCornerShape(22.dp)).background(product.accent.copy(alpha = .12f)),
                contentAlignment = Alignment.Center
            ) {
                ProductArt(product.accent, product.shape, Modifier.size(width = 50.dp, height = 66.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(product.name, color = Cocoa, fontSize = 15.sp, lineHeight = 18.sp, fontWeight = FontWeight.Black, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(product.unit, color = InkMuted, fontSize = 12.sp, modifier = Modifier.padding(top = 3.dp))
                Text("${quantity} caja(s) · ${quantity * product.unitsPerBox} unidades", color = product.accent, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 3.dp))
                Text(money(product.price * product.unitsPerBox * quantity), color = Mint, fontSize = 18.sp, fontWeight = FontWeight.Black, modifier = Modifier.padding(top = 5.dp))
            }
            QuantityStepper(quantity, onAdd, onRemove)
        }
    }
}

@Composable
fun QuantityStepper(quantity: Int, onAdd: () -> Unit, onRemove: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Surface(color = Purple, shape = CircleShape, modifier = Modifier.clickable(onClick = onAdd)) {
            Icon(Icons.Default.Add, contentDescription = "Aumentar", tint = Color.White, modifier = Modifier.padding(8.dp).size(18.dp))
        }
        Text(quantity.toString(), color = Cocoa, fontSize = 18.sp, fontWeight = FontWeight.Black)
        Surface(color = Lilac, shape = CircleShape, modifier = Modifier.clickable(onClick = onRemove)) {
            Icon(Icons.Default.Remove, contentDescription = "Disminuir", tint = DeepPurple, modifier = Modifier.padding(8.dp).size(18.dp))
        }
    }
}

@Composable
fun CartSummaryCard(
    subtotal: Double,
    oldSubtotal: Double,
    savings: Double,
    missing: Double,
    canOrder: Boolean,
    onPlaceOrder: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = DeepPurple),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Resumen del pedido", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
            SummaryLine("Precio lista", money(oldSubtotal), Color.White.copy(alpha = .72f))
            SummaryLine("Ahorro aplicado", "- ${money(savings)}", Honey)
            SummaryLine("Total", money(subtotal), Color.White, highlight = true)
            if (!canOrder) {
                Surface(color = Coral.copy(alpha = .18f), shape = RoundedCornerShape(16.dp)) {
                    Text("Faltan ${money(missing)} para activar despacho.", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp))
                }
            }
            Button(
                onClick = onPlaceOrder,
                enabled = canOrder,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Honey, contentColor = Cocoa, disabledContainerColor = Color.White.copy(alpha = .20f), disabledContentColor = Color.White.copy(alpha = .55f))
            ) {
                Text("Confirmar pedido", fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
fun SummaryLine(label: String, value: String, color: Color, highlight: Boolean = false) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = color.copy(alpha = if (highlight) 1f else .82f), fontSize = if (highlight) 17.sp else 14.sp, fontWeight = if (highlight) FontWeight.Black else FontWeight.Bold)
        Text(value, color = color, fontSize = if (highlight) 24.sp else 15.sp, fontWeight = FontWeight.Black)
    }
}

@Composable
fun EmptyState(title: String, subtitle: String) {
    Card(shape = RoundedCornerShape(30.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(3.dp)) {
        Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            MascotBubble()
            Text(title, color = Cocoa, fontSize = 22.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 16.dp))
            Text(subtitle, color = InkMuted, fontSize = 14.sp, lineHeight = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 6.dp))
        }
    }
}

@Composable
fun OrdersScreen(padding: PaddingValues, latestOrder: Order?, orders: List<Order>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Cream).padding(padding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Text("Sigue el despacho", color = Cocoa, fontSize = 32.sp, fontWeight = FontWeight.Black)
            Text(latestOrder?.let { "Pedido ${it.code} camino a tu tienda" } ?: "Aun no tienes pedidos confirmados", color = InkMuted)
        }
        if (latestOrder == null) {
            item { EmptyState("Sin pedidos todavia", "Cuando confirmes el carrito veras aqui el avance del despacho.") }
        } else {
            item {
                Card(shape = RoundedCornerShape(30.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                        Text("Progreso del pedido", color = Cocoa, fontSize = 20.sp, fontWeight = FontWeight.Black)
                        LinearProgressIndicator(progress = { .78f }, modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(99.dp)), color = Mint, trackColor = Lilac)
                        SummaryLine("Total", money(latestOrder.total), Purple, highlight = true)
                        SummaryLine("Cajas", latestOrder.boxes.toString(), Purple)
                        OrderStep("Revision de pedido", "Stock y precios validados.", true)
                        OrderStep("Pedido facturado", "Comprobante listo.", true)
                        OrderStep("Pedido en camino", "Despacho asignado.", true)
                        OrderStep("Pedido entregado", "Pendiente de confirmacion.", false)
                    }
                }
            }
            item { RatingCard() }
            if (orders.size > 1) {
                item { Text("Historial", color = Cocoa, fontSize = 22.sp, fontWeight = FontWeight.Black) }
                items(orders.drop(1)) { order -> OrderHistoryRow(order) }
            }
        }
    }
}

@Composable
fun OrderHistoryRow(order: Order) {
    Surface(color = Color.White, shape = RoundedCornerShape(20.dp), shadowElevation = 2.dp) {
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(order.code, color = Cocoa, fontWeight = FontWeight.Black)
                Text(order.status, color = InkMuted, fontSize = 12.sp)
            }
            Text(money(order.total), color = Mint, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun OrderStep(title: String, subtitle: String, done: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(42.dp).clip(CircleShape).background(if (done) Mint else Purple),
            contentAlignment = Alignment.Center
        ) {
            if (done) Icon(Icons.Default.Check, contentDescription = null, tint = Color.White) else Text("4", color = Color.White, fontWeight = FontWeight.Black)
        }
        Spacer(Modifier.width(14.dp))
        Column {
            Text(title, color = if (done) InkMuted else Cocoa, fontSize = 18.sp, fontWeight = FontWeight.Black)
            Text(subtitle, color = InkMuted, fontSize = 13.sp)
        }
    }
}

@Composable
fun RatingCard() {
    Card(shape = RoundedCornerShape(28.dp), colors = CardDefaults.cardColors(containerColor = DeepPurple), elevation = CardDefaults.cardElevation(4.dp)) {
        Column(Modifier.padding(20.dp)) {
            Text("Califica tu servicio", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Black)
            Text("Ayudanos a mejorar la entrega a distribuidores.", color = Color.White.copy(alpha = .72f), modifier = Modifier.padding(top = 4.dp))
            Row(Modifier.padding(top = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(5) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Honey, modifier = Modifier.size(34.dp))
                }
            }
        }
    }
}

@Composable
fun BusinessScreen(padding: PaddingValues, cartItems: Int, savings: Double, orders: List<Order>) {
    Column(
        modifier = Modifier.fillMaxSize().background(Cream).padding(padding).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mi negocio", color = Cocoa, fontSize = 32.sp, fontWeight = FontWeight.Black)
        Card(shape = RoundedCornerShape(30.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(4.dp)) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BrandMark(60.dp)
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text("Distribuidores Exclusivos", color = Cocoa, fontSize = 20.sp, fontWeight = FontWeight.Black)
                        Text("Cliente preferente Universal", color = InkMuted)
                    }
                }
                Surface(color = Lilac.copy(alpha = .55f), shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Text("Resumen de compra", color = Cocoa, fontWeight = FontWeight.Black)
                        Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Productos en carrito", color = InkMuted)
                            Text(cartItems.toString(), color = Purple, fontWeight = FontWeight.Black)
                        }
                        Row(Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Ahorro estimado", color = InkMuted)
                            Text(money(savings), color = Mint, fontWeight = FontWeight.Black)
                        }
                        Row(Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Pedidos confirmados", color = InkMuted)
                            Text(orders.size.toString(), color = Purple, fontWeight = FontWeight.Black)
                        }
                    }
                }
                BusinessInsightCard()
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Purple)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Revisar carrito", fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
fun BusinessInsightCard() {
    Surface(color = DeepPurple, shape = RoundedCornerShape(22.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("Sugerencia de reposicion", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Black)
            Text("Gelatinas y flanes tienen mejor rotacion esta semana. Arma un combo de 6 cajas para mejorar margen.", color = Color.White.copy(alpha = .76f), fontSize = 13.sp, lineHeight = 17.sp)
            LinearProgressIndicator(progress = { .68f }, modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(99.dp)), color = Honey, trackColor = Color.White.copy(alpha = .18f))
            Text("68% de meta semanal estimada", color = Honey, fontSize = 12.sp, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun Pill(text: String, background: Color, foreground: Color) {
    Surface(color = background, contentColor = foreground, shape = RoundedCornerShape(999.dp)) {
        Text(
            text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Preview(name = "Unidulza - Inicio Impactante", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun HomeScreenPreview() {
    UnidulzaTheme {
        HomeScreen(PaddingValues(0.dp), cartItems = 4, subtotal = 96.40, savings = 11.20, onAdd = {}, onOpenCart = {})
    }
}

@Preview(name = "Unidulza - Promociones", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PromoScreenPreview() {
    UnidulzaTheme {
        PromoScreen(PaddingValues(0.dp), onAdd = {})
    }
}

@Preview(name = "Unidulza - Pedido", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun OrdersScreenPreview() {
    UnidulzaTheme {
        OrdersScreen(PaddingValues(0.dp), Order("UN-2486", 168.40, 6), listOf(Order("UN-2486", 168.40, 6)))
    }
}

@Preview(name = "Unidulza - Carrito", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun CartScreenPreview() {
    UnidulzaTheme {
        CartScreen(
            padding = PaddingValues(0.dp),
            cart = mapOf(products[0].sku to 2, products[2].sku to 3),
            subtotal = products[0].price * products[0].unitsPerBox * 2 + products[2].price * products[2].unitsPerBox * 3,
            oldSubtotal = products[0].oldPrice * products[0].unitsPerBox * 2 + products[2].oldPrice * products[2].unitsPerBox * 3,
            savings = (products[0].oldPrice - products[0].price) * products[0].unitsPerBox * 2 + (products[2].oldPrice - products[2].price) * products[2].unitsPerBox * 3,
            minimumOrder = 120.0,
            onAdd = {},
            onRemove = {},
            onPlaceOrder = {}
        )
    }
}
