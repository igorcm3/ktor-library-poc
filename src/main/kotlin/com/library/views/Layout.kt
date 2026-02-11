package com.library.views

import kotlinx.html.*

fun HTML.layout(title: String, activeMenu: String = "", content: FlowContent.() -> Unit) {
    head {
        meta(charset = "UTF-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        title { +title }
        link(rel = "preconnect", href = "https://fonts.googleapis.com")
        link(rel = "preconnect", href = "https://fonts.gstatic.com") {
            attributes["crossorigin"] = ""
        }
        link(href = "https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap", rel = "stylesheet")
        script { src = "https://cdn.tailwindcss.com" }
        script {
            unsafe {
                raw("""
                    tailwind.config = {
                        theme: {
                            extend: {
                                colors: {
                                    primary: {
                                        50: '#eff6ff',
                                        100: '#dbeafe',
                                        200: '#bfdbfe',
                                        300: '#93c5fd',
                                        400: '#60a5fa',
                                        500: '#3b82f6',
                                        600: '#2563eb',
                                        700: '#1d4ed8',
                                        800: '#1e40af',
                                        900: '#1e3a8a',
                                    }
                                },
                                fontFamily: {
                                    sans: ['Inter', 'system-ui', 'sans-serif'],
                                }
                            }
                        }
                    }
                """.trimIndent())
            }
        }
    }
    body(classes = "bg-gray-50 min-h-screen") {
        // Navbar
        nav(classes = "bg-white shadow-sm border-b border-gray-200 sticky top-0 z-50") {
            div(classes = "container mx-auto px-4") {
                div(classes = "flex items-center justify-between h-16") {
                    a(href = "/", classes = "flex items-center space-x-3 group") {
                        div(classes = "w-10 h-10 bg-gradient-to-br from-primary-600 to-primary-700 rounded-lg flex items-center justify-center text-white font-bold text-xl shadow-lg group-hover:shadow-xl transition-shadow") {
                            +"📚"
                        }
                        span(classes = "text-xl font-bold text-gray-900") { +"BiblioTech" }
                    }
                    
                    div(classes = "flex items-center space-x-1") {
                        a(href = "/", classes = "px-4 py-2 rounded-lg font-medium transition-colors ${if (activeMenu == "home") "bg-primary-50 text-primary-700" else "text-gray-600 hover:bg-gray-100"}") {
                            +"Home"
                        }
                        a(href = "/livros", classes = "px-4 py-2 rounded-lg font-medium transition-colors ${if (activeMenu == "livros") "bg-primary-50 text-primary-700" else "text-gray-600 hover:bg-gray-100"}") {
                            +"Livros"
                        }
                        a(href = "/autores", classes = "px-4 py-2 rounded-lg font-medium transition-colors ${if (activeMenu == "autores") "bg-primary-50 text-primary-700" else "text-gray-600 hover:bg-gray-100"}") {
                            +"Autores"
                        }
                        a(href = "/swagger", classes = "px-4 py-2 rounded-lg font-medium text-gray-600 hover:bg-gray-100 transition-colors") {
                            +"API Docs"
                        }
                    }
                }
            }
        }
        
        // Main Content
        main(classes = "container mx-auto px-4 py-8") {
            content()
        }
        
        // Footer
        footer(classes = "bg-white border-t border-gray-200 mt-16") {
            div(classes = "container mx-auto px-4 py-8") {
                div(classes = "text-center text-gray-600 text-sm") {
                    +"© 2026 BiblioTech - Sistema de Gerenciamento de Biblioteca"
                    br {}
                    +"Desenvolvido com "
                    span(classes = "text-red-500") { +"♥" }
                    +" usando Ktor + Kotlin"
                }
            }
        }
    }
}
