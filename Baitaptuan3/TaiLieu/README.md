Thành phần/hàm chính đã sử dụng
Khung ứng dụng & điều hướng
Scaffold, CenterAlignedTopAppBar, IconButton, Icons.AutoMirrored.Filled.ArrowBack
rememberNavController, NavHost, composable, nav.navigate(...), nav.popBackStack()
Danh sách & thẻ
LazyColumn, items, ElevatedCard, RoundedCornerShape, Spacer
Bố cục & nền tảng
Column, Row, Box, Modifier.fillMaxSize/fillMaxWidth/height/size/padding/clip/background
Alignment, Arrangement.spacedBy, Divider
Văn bản (Text Detail)
buildAnnotatedString, withStyle(SpanStyle(...))
TextDecoration.LineThrough/Underline, FontWeight, FontStyle.Italic, FontFamily.Serif
Ảnh
AsyncImage (Coil) cho URL, Image + painterResource cho ảnh local
LocalUriHandler, clickable, verticalScroll, rememberScrollState
Nhập liệu
OutlinedTextField, remember { mutableStateOf(...) }
PasswordVisualTransformation, VisualTransformation.None
KeyboardOptions(keyboardType = KeyboardType.Password)
Nút “con mắt”: Icons.Filled.Visibility / VisibilityOff trong trailingIcon
Demo bố cục
Row/Column với ô màu, aspectRatio, background (sử dụng MaterialTheme.colorScheme.primary)
Size & Slider
Slider điều chỉnh fontSize của Text
Slider điều chỉnh kích thước & bo góc ảnh (RoundedCornerShape(radius.dp))