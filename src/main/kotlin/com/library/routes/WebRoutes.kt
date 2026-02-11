package com.library.routes

import com.library.dto.AutorResponse
import com.library.dto.LivroResponse
import com.library.service.AutorService
import com.library.service.LivroService
import com.library.views.layout
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Route.webRoutes(autorService: AutorService, livroService: LivroService) {
    
    get("/") {
        val livros = livroService.getAll()
        val autores = autorService.getAll()
        
        call.respondHtml {
            layout("Home - BiblioTech", "home") {
                // Hero Section Moderna
                div(classes = "relative overflow-hidden bg-white mb-12 -mx-4 -mt-8") {
                    div(classes = "absolute inset-0 bg-gradient-to-br from-primary-50 via-white to-purple-50 opacity-70") {}
                    div(classes = "absolute inset-0" ) {
                        attributes["style"] = "background-image: radial-gradient(circle at 1px 1px, rgb(203 213 225 / 0.15) 1px, transparent 0); background-size: 40px 40px;"
                    }
                    
                    div(classes = "relative container mx-auto px-4 py-20") {
                        div(classes = "max-w-4xl") {
                            div(classes = "inline-flex items-center bg-primary-100 text-primary-700 px-4 py-2 rounded-full text-sm font-semibold mb-6") {
                                span(classes = "animate-pulse mr-2") { +"●" }
                                +"Sistema de Gerenciamento de Biblioteca"
                            }
                            
                            h1(classes = "text-6xl md:text-7xl font-bold text-gray-900 mb-6 leading-tight") {
                                +"Sua biblioteca "
                                br {}
                                span(classes = "text-transparent bg-clip-text bg-gradient-to-r from-primary-600 to-purple-600") {
                                    +"digital completa"
                                }
                            }
                            
                            p(classes = "text-xl text-gray-600 mb-10 max-w-2xl") {
                                +"Organize, gerencie e explore sua coleção de livros de forma moderna e intuitiva. Tudo em um só lugar."
                            }
                            
                            div(classes = "flex flex-wrap gap-4") {
                                a(href = "/livros/novo", classes = "group inline-flex items-center gap-2 bg-primary-600 text-white px-8 py-4 rounded-xl font-semibold hover:bg-primary-700 transition-all shadow-lg hover:shadow-xl hover:scale-105") {
                                    span { +"+" }
                                    span { +"Adicionar Livro" }
                                    span(classes = "group-hover:translate-x-1 transition-transform") { +"→" }
                                }
                                a(href = "/autores/novo", classes = "inline-flex items-center gap-2 bg-white text-gray-700 px-8 py-4 rounded-xl font-semibold hover:bg-gray-50 transition-all border-2 border-gray-200") {
                                    span { +"✍️" }
                                    span { +"Adicionar Autor" }
                                }
                            }
                        }
                    }
                }
                
                // Stats Cards
                div(classes = "grid grid-cols-1 md:grid-cols-3 gap-6 mb-16") {
                    div(classes = "bg-gradient-to-br from-blue-50 to-blue-100 rounded-2xl p-8 border border-blue-200 shadow-sm hover:shadow-md transition-shadow") {
                        div(classes = "flex items-center justify-between") {
                            div {
                                p(classes = "text-blue-600 text-sm font-semibold mb-2") { +"Total de Livros" }
                                p(classes = "text-4xl font-bold text-blue-900") { +livros.size.toString() }
                            }
                            div(classes = "text-5xl text-blue-300") { +"📚" }
                        }
                    }
                    
                    div(classes = "bg-gradient-to-br from-purple-50 to-purple-100 rounded-2xl p-8 border border-purple-200 shadow-sm hover:shadow-md transition-shadow") {
                        div(classes = "flex items-center justify-between") {
                            div {
                                p(classes = "text-purple-600 text-sm font-semibold mb-2") { +"Autores Cadastrados" }
                                p(classes = "text-4xl font-bold text-purple-900") { +autores.size.toString() }
                            }
                            div(classes = "text-5xl text-purple-300") { +"✍️" }
                        }
                    }
                    
                    div(classes = "bg-gradient-to-br from-pink-50 to-pink-100 rounded-2xl p-8 border border-pink-200 shadow-sm hover:shadow-md transition-shadow") {
                        div(classes = "flex items-center justify-between") {
                            div {
                                p(classes = "text-pink-600 text-sm font-semibold mb-2") { +"Sua Biblioteca" }
                                p(classes = "text-4xl font-bold text-pink-900") { +"Ativa" }
                            }
                            div(classes = "text-5xl text-pink-300") { +"⭐" }
                        }
                    }
                }
                
                // Grid de Livros
                if (livros.isNotEmpty()) {
                    div(classes = "mb-16") {
                        h2(classes = "text-4xl font-bold text-gray-900 mb-8") { +"Seus Livros" }
                        
                        div(classes = "grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8") {
                            for ((index, livro) in livros.withIndex()) {
                                renderLivroCard(livro, index)
                            }
                        }
                    }
                } else {
                    div(classes = "text-center py-16 bg-gray-50 rounded-2xl mb-16") {
                        p(classes = "text-6xl mb-4") { +"📚" }
                        p(classes = "text-2xl font-semibold text-gray-900 mb-4") { +"Nenhum livro cadastrado" }
                        p(classes = "text-gray-600 mb-8") { +"Comece adicionando seu primeiro livro" }
                        a(href = "/livros/novo", classes = "inline-flex items-center gap-2 bg-primary-600 text-white px-6 py-3 rounded-xl font-semibold hover:bg-primary-700 transition-all") {
                            +"+ Adicionar Livro"
                        }
                    }
                }
                
                // Modal de Confirmação (HTML)
                renderDeleteModal()
            }
        }
    }
    
    get("/livros") {
        val livros = livroService.getAll()
        
        call.respondHtml {
            layout("Livros - BiblioTech", "livros") {
                div(classes = "mb-12") {
                    div(classes = "flex flex-col md:flex-row md:items-center md:justify-between mb-8") {
                        h1(classes = "text-4xl font-bold text-gray-900") { +"Meus Livros" }
                        a(href = "/livros/novo", classes = "inline-flex items-center gap-2 bg-primary-600 text-white px-6 py-3 rounded-xl font-semibold hover:bg-primary-700 transition-all mt-4 md:mt-0") {
                            span { +"+" }
                            span { +"Novo Livro" }
                        }
                    }
                    
                    if (livros.isEmpty()) {
                        div(classes = "text-center py-16 bg-gray-50 rounded-2xl") {
                            p(classes = "text-6xl mb-4") { +"📚" }
                            p(classes = "text-2xl font-semibold text-gray-900 mb-4") { +"Nenhum livro cadastrado" }
                            p(classes = "text-gray-600 mb-8") { +"Crie seu primeiro livro para começar" }
                            a(href = "/livros/novo", classes = "inline-flex items-center gap-2 bg-primary-600 text-white px-6 py-3 rounded-xl font-semibold hover:bg-primary-700 transition-all") {
                                +"+ Adicionar Livro"
                            }
                        }
                    } else {
                        div(classes = "grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8") {
                            for ((index, livro) in livros.withIndex()) {
                                renderLivroCard(livro, index)
                            }
                        }
                    }
                }
                renderDeleteModal()
            }
        }
    }
    
    get("/livros/novo") {
        val autores = autorService.getAll()
        
        call.respondHtml {
            layout("Novo Livro - BiblioTech", "livros") {
                div(classes = "max-w-2xl mx-auto") {
                    h1(classes = "text-4xl font-bold text-gray-900 mb-4") { +"Adicionar Novo Livro" }
                    p(classes = "text-gray-600 mb-12") { +"Preencha os dados do livro abaixo" }
                    
                    form(classes = "bg-white rounded-2xl shadow-lg p-8 border border-gray-200") {
                        id = "livroForm"
                        
                        div(classes = "mb-8") {
                            label(classes = "block text-sm font-semibold text-gray-700 mb-3") {
                                htmlFor = "titulo"
                                +"Título"
                            }
                            input(type = InputType.text, classes = "w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-600 focus:outline-none transition-colors font-medium") {
                                id = "titulo"
                                name = "titulo"
                                placeholder = "Digite o título do livro"
                                required = true
                            }
                        }
                        
                        div(classes = "mb-8") {
                            label(classes = "block text-sm font-semibold text-gray-700 mb-3") {
                                htmlFor = "isbn"
                                +"ISBN"
                            }
                            input(type = InputType.text, classes = "w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-600 focus:outline-none transition-colors font-medium") {
                                id = "isbn"
                                name = "isbn"
                                placeholder = "Digite o ISBN do livro"
                                required = true
                            }
                        }
                        
                        div(classes = "mb-8") {
                            label(classes = "block text-sm font-semibold text-gray-700 mb-3") {
                                htmlFor = "anoPublicacao"
                                +"Ano de Publicação"
                            }
                            input(type = InputType.number, classes = "w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-600 focus:outline-none transition-colors font-medium") {
                                id = "anoPublicacao"
                                name = "anoPublicacao"
                                placeholder = "2024"
                                required = true
                            }
                        }
                        
                        div(classes = "mb-8") {
                            label(classes = "block text-sm font-semibold text-gray-700 mb-3") {
                                htmlFor = "autorId"
                                +"Autor"
                            }
                            select(classes = "w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-600 focus:outline-none transition-colors font-medium") {
                                id = "autorId"
                                name = "autorId"
                                required = true
                                option {
                                    value = ""
                                    +"Selecione um autor"
                                }
                                for (autor in autores) {
                                    option {
                                        value = autor.id.toString()
                                        +autor.nome
                                    }
                                }
                            }
                        }
                        
                        div(classes = "flex gap-4 pt-4") {
                            button(type = ButtonType.submit, classes = "flex-1 bg-primary-600 text-white px-8 py-3 rounded-xl font-semibold hover:bg-primary-700 transition-all") {
                                +"Salvar Livro"
                            }
                            a(href = "/livros", classes = "flex-1 text-center bg-gray-200 text-gray-700 px-8 py-3 rounded-xl font-semibold hover:bg-gray-300 transition-all") {
                                +"Cancelar"
                            }
                        }
                    }
                    
                    script {
                        unsafe {
                            raw("""
                                document.getElementById('livroForm').addEventListener('submit', async (e) => {
                                    e.preventDefault();
                                    const formData = new FormData(e.target);
                                    const data = {
                                        titulo: formData.get('titulo'),
                                        isbn: formData.get('isbn'),
                                        anoPublicacao: parseInt(formData.get('anoPublicacao')),
                                        autorId: parseInt(formData.get('autorId'))
                                    };
                                    
                                    try {
                                        const response = await fetch('/api/livros', {
                                            method: 'POST',
                                            headers: { 'Content-Type': 'application/json' },
                                            body: JSON.stringify(data)
                                        });
                                        
                                        if (response.ok) {
                                            window.location.href = '/livros';
                                        } else {
                                            const error = await response.json();
                                            alert('Erro ao salvar: ' + (error.message || 'Erro desconhecido'));
                                        }
                                    } catch (error) {
                                        alert('Erro ao salvar livro: ' + error.message);
                                    }
                                });
                            """)
                        }
                    }
                }
            }
        }
    }
    
    get("/autores") {
        val autores = autorService.getAll()
        
        call.respondHtml {
            layout("Autores - BiblioTech", "autores") {
                div(classes = "mb-12") {
                    div(classes = "flex flex-col md:flex-row md:items-center md:justify-between mb-8") {
                        h1(classes = "text-4xl font-bold text-gray-900") { +"Meus Autores" }
                        a(href = "/autores/novo", classes = "inline-flex items-center gap-2 bg-primary-600 text-white px-6 py-3 rounded-xl font-semibold hover:bg-primary-700 transition-all mt-4 md:mt-0") {
                            span { +"+" }
                            span { +"Novo Autor" }
                        }
                    }
                    
                    if (autores.isEmpty()) {
                        div(classes = "text-center py-16 bg-gray-50 rounded-2xl") {
                            p(classes = "text-6xl mb-4") { +"✍️" }
                            p(classes = "text-2xl font-semibold text-gray-900 mb-4") { +"Nenhum autor cadastrado" }
                            p(classes = "text-gray-600 mb-8") { +"Crie seu primeiro autor para começar" }
                            a(href = "/autores/novo", classes = "inline-flex items-center gap-2 bg-primary-600 text-white px-6 py-3 rounded-xl font-semibold hover:bg-primary-700 transition-all") {
                                +"+ Adicionar Autor"
                            }
                        }
                    } else {
                        div(classes = "grid grid-cols-1 md:grid-cols-2 gap-8") {
                            for (autor in autores) {
                                renderAutorCard(autor)
                            }
                        }
                    }
                }
                renderDeleteModal()
            }
        }
    }
    
    get("/autores/novo") {
        call.respondHtml {
            layout("Novo Autor - BiblioTech", "autores") {
                div(classes = "max-w-2xl mx-auto") {
                    h1(classes = "text-4xl font-bold text-gray-900 mb-4") { +"Adicionar Novo Autor" }
                    p(classes = "text-gray-600 mb-12") { +"Preencha os dados do autor abaixo" }
                    
                    form(classes = "bg-white rounded-2xl shadow-lg p-8 border border-gray-200") {
                        id = "autorForm"
                        
                        div(classes = "mb-8") {
                            label(classes = "block text-sm font-semibold text-gray-700 mb-3") {
                                htmlFor = "nome"
                                +"Nome"
                            }
                            input(type = InputType.text, classes = "w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-600 focus:outline-none transition-colors font-medium") {
                                id = "nome"
                                name = "nome"
                                placeholder = "Digite o nome do autor"
                                required = true
                            }
                        }
                        
                        div(classes = "mb-8") {
                            label(classes = "block text-sm font-semibold text-gray-700 mb-3") {
                                htmlFor = "nacionalidade"
                                +"Nacionalidade"
                            }
                            input(type = InputType.text, classes = "w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-600 focus:outline-none transition-colors font-medium") {
                                id = "nacionalidade"
                                name = "nacionalidade"
                                placeholder = "Digite a nacionalidade do autor"
                            }
                        }
                        
                        div(classes = "flex gap-4 pt-4") {
                            button(type = ButtonType.submit, classes = "flex-1 bg-primary-600 text-white px-8 py-3 rounded-xl font-semibold hover:bg-primary-700 transition-all") {
                                +"Salvar Autor"
                            }
                            a(href = "/autores", classes = "flex-1 text-center bg-gray-200 text-gray-700 px-8 py-3 rounded-xl font-semibold hover:bg-gray-300 transition-all") {
                                +"Cancelar"
                            }
                        }
                    }
                    
                    script {
                        unsafe {
                            raw("""
                                document.getElementById('autorForm').addEventListener('submit', async (e) => {
                                    e.preventDefault();
                                    const formData = new FormData(e.target);
                                    const data = {
                                        nome: formData.get('nome'),
                                        nacionalidade: formData.get('nacionalidade') || null
                                    };
                                    
                                    try {
                                        const response = await fetch('/api/autores', {
                                            method: 'POST',
                                            headers: { 'Content-Type': 'application/json' },
                                            body: JSON.stringify(data)
                                        });
                                        
                                        if (response.ok) {
                                            window.location.href = '/autores';
                                        } else {
                                            const error = await response.json();
                                            alert('Erro ao salvar: ' + (error.message || 'Erro desconhecido'));
                                        }
                                    } catch (error) {
                                        alert('Erro ao salvar autor: ' + error.message);
                                    }
                                });
                            """)
                        }
                    }
                }
            }
        }
    }
}

// Função auxiliar para renderizar card de livro com gradientes variados
private fun FlowContent.renderLivroCard(livro: LivroResponse, index: Int) {
    val gradients = listOf(
        "from-blue-50 to-blue-100 border-blue-200" to "bg-blue-100 text-blue-700",
        "from-purple-50 to-purple-100 border-purple-200" to "bg-purple-100 text-purple-700",
        "from-pink-50 to-pink-100 border-pink-200" to "bg-pink-100 text-pink-700",
        "from-green-50 to-green-100 border-green-200" to "bg-green-100 text-green-700",
        "from-orange-50 to-orange-100 border-orange-200" to "bg-orange-100 text-orange-700",
        "from-red-50 to-red-100 border-red-200" to "bg-red-100 text-red-700",
        "from-indigo-50 to-indigo-100 border-indigo-200" to "bg-indigo-100 text-indigo-700",
        "from-cyan-50 to-cyan-100 border-cyan-200" to "bg-cyan-100 text-cyan-700"
    )
    
    val (gradient, badge) = gradients[index % gradients.size]
    val emojis = listOf("📖", "📚", "📕", "📗", "📘", "📙", "📓", "📔")
    val emoji = emojis[index % emojis.size]
    
    div(classes = "bg-gradient-to-br $gradient rounded-2xl p-8 border-2 shadow-md hover:shadow-xl transition-all hover:scale-105 group") {
        div(classes = "flex justify-between items-start mb-6") {
            div(classes = "$badge rounded-xl px-3 py-2 text-2xl") { +emoji }
            span(classes = "text-gray-400 hover:text-red-600 transition-colors opacity-0 group-hover:opacity-100 text-2xl cursor-pointer") { 
                attributes["onclick"] = "openDeleteModal('${livro.id}', '${livro.titulo.replace("'", "\\'")}', 'livro')"
                +"✕"
            }
        }
        
        h3(classes = "text-2xl font-bold text-gray-900 mb-3 line-clamp-2") { +livro.titulo }
        
        p(classes = "text-gray-700 mb-6 line-clamp-3 text-sm leading-relaxed") { 
            +"ISBN: ${livro.isbn}"
        }
        
        div(classes = "space-y-3 border-t border-current border-opacity-20 pt-4") {
            div(classes = "flex justify-between text-sm") {
                span(classes = "text-gray-600") { +"Ano:" }
                span(classes = "font-semibold text-gray-900") { +livro.anoPublicacao.toString() }
            }
            
            div(classes = "flex justify-between text-sm") {
                span(classes = "text-gray-600") { +"Autor:" }
                span(classes = "font-semibold text-gray-900") { +livro.autor.nome }
            }
        }
    }
}

// Função auxiliar para renderizar card de autor
private fun FlowContent.renderAutorCard(autor: AutorResponse) {
    val gradients = listOf(
        "from-amber-50 to-amber-100 border-amber-200" to "bg-amber-100",
        "from-lime-50 to-lime-100 border-lime-200" to "bg-lime-100",
        "from-teal-50 to-teal-100 border-teal-200" to "bg-teal-100",
        "from-sky-50 to-sky-100 border-sky-200" to "bg-sky-100"
    )
    
    val gradient = gradients[autor.id.toInt().mod(gradients.size)]
    
    div(classes = "bg-gradient-to-br ${gradient.first} rounded-2xl p-8 border-2 shadow-md hover:shadow-xl transition-all hover:scale-105 group") {
        div(classes = "flex justify-between items-start mb-6") {
            div(classes = "${gradient.second} rounded-xl px-4 py-2 text-3xl") { +"✍️" }
            span(classes = "text-gray-400 hover:text-red-600 transition-colors opacity-0 group-hover:opacity-100 text-2xl cursor-pointer") { 
                attributes["onclick"] = "openDeleteModal('${autor.id}', '${autor.nome.replace("'", "\\'")}', 'autor')"
                +"✕"
            }
        }
        
        h3(classes = "text-2xl font-bold text-gray-900 mb-3") { +autor.nome }
        
        p(classes = "text-gray-700 line-clamp-4 text-sm leading-relaxed") { 
            +(autor.nacionalidade ?: "Nacionalidade não informada")
        }
    }
}

// Função auxiliar para renderizar modal de confirmação
private fun FlowContent.renderDeleteModal() {
    // Modal backdrop
    div(classes = "fixed inset-0 bg-black bg-opacity-50 hidden z-40" ) {
        attributes["id"] = "deleteModalBackdrop"
        attributes["onclick"] = "closeDeleteModal()"
    }
    
    // Modal container
    div(classes = "fixed inset-0 flex items-center justify-center hidden z-50 p-4") {
        attributes["id"] = "deleteModalContainer"
        
        div(classes = "bg-white rounded-2xl shadow-2xl max-w-md w-full overflow-hidden transform transition-all" ) {
            // Header
            div(classes = "bg-gradient-to-r from-red-50 to-red-100 px-8 py-6 border-b border-red-200") {
                h2(classes = "text-2xl font-bold text-red-900") { +"Confirmar Exclusão" }
            }
            
            // Body
            div(classes = "px-8 py-6") {
                p(classes = "text-gray-700 mb-2") { 
                    +"Tem certeza que deseja excluir "
                    span(classes = "font-semibold text-red-600") { +"\u0022" }
                    span(classes = "font-bold text-gray-900") { attributes["id"] = "deleteItemName"; +" " }
                    span(classes = "font-semibold text-red-600") { +"\u0022" }
                    +"?"
                }
                p(classes = "text-sm text-gray-600") { 
                    +"Esta ação é irreversível e não pode ser desfeita."
                }
            }
            
            // Footer
            div(classes = "bg-gray-50 px-8 py-4 border-t border-gray-200 flex gap-3") {
                button(classes = "flex-1 px-4 py-2 bg-gray-200 text-gray-700 rounded-lg font-semibold hover:bg-gray-300 transition-colors", 
                    type = ButtonType.button) {
                    attributes["onclick"] = "closeDeleteModal()"
                    +"Cancelar"
                }
                button(classes = "flex-1 px-4 py-2 bg-red-600 text-white rounded-lg font-semibold hover:bg-red-700 transition-colors",
                    type = ButtonType.button) {
                    attributes["id"] = "confirmDeleteBtn"
                    attributes["onclick"] = "confirmDelete()"
                    +"Excluir"
                }
            }
        }
    }
    
    // JavaScript para gerenciar modal
    script {
        unsafe {
            +"""
            let deleteData = { id: null, type: null };
            
            function openDeleteModal(id, name, type) {
                deleteData.id = id;
                deleteData.type = type;
                document.getElementById('deleteItemName').textContent = name;
                document.getElementById('deleteModalBackdrop').classList.remove('hidden');
                document.getElementById('deleteModalContainer').classList.remove('hidden');
            }
            
            function closeDeleteModal() {
                document.getElementById('deleteModalBackdrop').classList.add('hidden');
                document.getElementById('deleteModalContainer').classList.add('hidden');
                deleteData = { id: null, type: null };
            }
            
            function confirmDelete() {
                if (deleteData.id && deleteData.type) {
                    const url = deleteData.type === 'livro' 
                        ? '/api/livros/' + deleteData.id
                        : '/api/autores/' + deleteData.id;
                    
                    fetch(url, { method: 'DELETE' })
                        .then(() => {
                            closeDeleteModal();
                            setTimeout(() => location.reload(), 300);
                        })
                        .catch(err => {
                            alert('Erro ao excluir item');
                            console.error(err);
                            closeDeleteModal();
                        });
                }
            }
            
            // Fechar modal ao clicar fora
            document.getElementById('deleteModalBackdrop')?.addEventListener('click', closeDeleteModal);
            """
        }
    }
}
