Cover for Zalada applictions

Developing...

![android1](https://github.com/user-attachments/assets/28c14ea0-6ac5-4c9b-b297-bf8e355c78c1)

## Project structure

The app follows a layer-first clean architecture. Dependencies only point inwards:
`presentation → domain ← data`, and `domain` has no Android framework dependencies.

```
com.thoen.zaladaandroid
├── MainActivity.kt                # Entry point, hosts the theme + navigation
├── core/
│   ├── common/                    # UiState, price formatting
│   └── di/ServiceLocator.kt       # Manual DI graph (swap for Hilt/Koin later)
├── domain/                        # Pure Kotlin business rules
│   ├── model/                     # Product, Category, CartItem, NotificationItem
│   ├── repository/                # Repository contracts
│   └── usecase/                   # One class per action, invoked with operator fun invoke()
├── data/                          # Implementations of the domain contracts
│   ├── model/                     # DTOs (ProductDto, CartItemDto, ...)
│   ├── mapper/                    # DTO → domain mappers
│   ├── source/                    # Local data sources (in-memory sample data)
│   └── repository/                # ProductRepositoryImpl, CartRepositoryImpl, ...
└── presentation/                  # Compose UI
    ├── component/                 # Reusable widgets (ButtonBack, ShapeIcon, ProductGrid, ...)
    ├── theme/                     # Color, Type, Theme
    ├── navigation/                # Destinations + AppRouter
    ├── home/                      # HomeScreen, ProductAllScreen, DetailProductScreen + ViewModels
    │   └── component/             # Feature widgets (Banner, ProductCard, GridProductCard, ...)
    ├── authentication/
    ├── cart/ + component/
    ├── favorite/
    ├── main/
    ├── notification/ + component/
    ├── profile/ + component/
    └── search/ + model/
```

Conventions:

- Screens never touch data sources directly. They collect `StateFlow` exposed by a `ViewModel`
  and forward user actions back to it.
- ViewModels are created with `viewModel(factory = XViewModel.Factory)`; the factory pulls its use
  cases from `ServiceLocator`. Replace `ServiceLocator` with Hilt when the module count grows.
- Repository interfaces live in `domain/repository`, implementations and DTO mapping in `data`.
- Sample data lives in `data/source/*LocalDataSource` so it can be replaced by a Retrofit-backed
  data source without changing the domain or presentation layers.

## Design system & responsive layout

- **Look** — 2026 "neon glass" style: deep navy canvas with violet/cyan light blooms, translucent
  frosted panels (`glassFill()` + `glassBorder()` with a neon glow), gradient pill buttons and
  circular glass icon buttons. Tokens live in `presentation/theme` (`Color.kt`, `Type.kt`,
  `Shape.kt`, `Theme.kt`) and the primitives in `presentation/component/Glass.kt`:
  `AppBackground`, `GlassCard`, `NeonButton`, `GlassIconButton`.
- **Every button and icon** uses those primitives: primary CTAs (`NeonButton`), app-bar/hero icons
  (`GlassIconButton`), back buttons, category tiles, product add buttons, quantity steppers,
  search chips and the floating bottom navigation pill.
- **Icons** are the app's own drawables (`res/drawable/*.png`) rendered as tinted images — nav
  pairs (`home_bulk`/`home_bold`), toolbar icons (`search_light`, `bag_light`, `arrow_left`),
  category icons, profile rows and cart actions. Category ids map to drawables in
  `presentation/component/AppIcons.kt`; two small vector drawables (`ic_close`,
  `ic_chevron_right`) fill the gaps. Product photos stay reusable images.
- **Light and dark** variants are both frosted-glass: `ZaladaAndroidTheme` swaps the navy canvas for
  a pale blue one and adjusts fill/border alpha via `glassFill()` / `glassBorder()`.
- **Theme menu** — Profile → *Appearance* → *Theme* lets the user pick **System default / Light /
  Dark**. The choice is persisted (`ThemeLocalDataSource`, SharedPreferences) through
  `ThemeRepository` + `ObserveThemeModeUseCase` / `SetThemeModeUseCase`, exposed app-wide by
  `ThemeViewModel` in `MainActivity` and shared with screens through `LocalThemeController`.
- **Screens never hard-code colors** — they read `MaterialTheme.colorScheme` + glass tokens, and
  Scaffolds are transparent so the app background shows through.
- **Smooth gradients** — `smoothGradient()` in `Color.kt` interpolates extra stops (Android renders
  gradients in 8-bit, which bands on dark screens), and glass blooms fade to the same hue at alpha 0
  instead of `Color.Transparent` (which darkens edges).
- **Promo banner pager** — `presentation/home/component/Banner.kt`: three story-style cards with
  per-page gradients, badge, CTA, image bloom, animated page dots and auto-advance every 4.5s.
  The card ratio adapts per breakpoint (1.58 / 2.05 / 2.45) and compact screens drop the subtitle so
  nothing gets clipped.
- **Breakpoints** (`presentation/component/WindowWidth.kt`, Material 3 sizes):
  - `< 600dp` compact — floating glass bottom bar, single pane.
  - `600–840dp` medium — glass navigation rail (icon only), adaptive grids.
  - `≥ 840dp` expanded — extended rail, two-pane product detail and two-pane cart with an order
    summary card, auth forms capped at 520dp.
- No fixed-width layouts: product grids use `GridCells.Adaptive`, cards size themselves, and
  horizontal carousels use a per-breakpoint width. This keeps the app usable on phones, foldables
  (inner/outer screens) and tablets.
