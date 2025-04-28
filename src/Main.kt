
data class Pokemon(val nome: String, val tipo: String, val hp: Int, val ataque: Int, val defesa: Int)


fun criarTime(nickname: String, todosPokemons: Map<String, Pokemon>, limite: Int): List<Pokemon> {
    val time = mutableListOf<Pokemon>()
    println("\n$nickname, escolha seus $limite Pokémon:")

    val listaDeNomes = todosPokemons.keys.toList()

    for (i in 1..limite) {
        println("\nEscolha o Pokémon número $i:")
        listaDeNomes.forEachIndexed { index, nome ->
            println("${index + 1}. $nome (${todosPokemons[nome]?.tipo})")
        }

        var escolha: Int? = null
        while (escolha == null || escolha < 1 || escolha > listaDeNomes.size || time.contains(todosPokemons[listaDeNomes[escolha - 1]])) {
            print("Digite o número do Pokémon desejado: ")
            try {
                escolha = readln().toIntOrNull()
                if (escolha == null || escolha < 1 || escolha > listaDeNomes.size) {
                    println("Escolha inválida. Digite um número da lista.")
                } else if (time.contains(todosPokemons[listaDeNomes[escolha - 1]])) {
                    println("Você já escolheu este Pokémon. Escolha outro.")
                }
            } catch (e: NumberFormatException) {
                println("Entrada inválida. Digite um número.")
            }
        }
        time.add(todosPokemons[listaDeNomes[escolha - 1]]!!)
        println("${todosPokemons[listaDeNomes[escolha - 1]]!!.nome} adicionado ao seu time.")
    }
    return time.toList()
}


fun atacar(atacante: Pokemon, defensor: Pokemon): Int {
    val danoBase = atacante.ataque
    val modificadorTipo = calcularModificadorTipo(atacante.tipo, defensor.tipo)
    val danoTotal = (danoBase * modificadorTipo).toInt()
    val danoComDefesa = (danoTotal - defensor.defesa).coerceAtLeast(1)
    println("${atacante.nome} (${atacante.tipo}) atacou ${defensor.nome} (${defensor.tipo}) e causou $danoComDefesa de dano!")
    return danoComDefesa
}


fun calcularModificadorTipo(tipoAtacante: String, tipoDefensor: String): Double {
    return when (tipoAtacante) {
        "FOGO" -> if (tipoDefensor == "GRAMA") 2.0 else 1.0
        "AGUA" -> if (tipoDefensor == "FOGO") 2.0 else 1.0
        "GRAMA" -> if (tipoDefensor == "AGUA") 2.0 else 1.0
        else -> 1.0
    }
}

fun batalhar(pokemonJogador1: Pokemon, pokemonJogador2: Pokemon): Pokemon? {
    println("\n--- Batalha: ${pokemonJogador1.nome} vs ${pokemonJogador2.nome} ---")
    var hpJogador1 = pokemonJogador1.hp
    var hpJogador2 = pokemonJogador2.hp

    while (hpJogador1 > 0 && hpJogador2 > 0) {

        val danoJogador1 = atacar(pokemonJogador1, pokemonJogador2)
        hpJogador2 -= danoJogador1
        println("${pokemonJogador2.nome} HP restante: $hpJogador2")
        if (hpJogador2 <= 0) break


        val danoJogador2 = atacar(pokemonJogador2, pokemonJogador1)
        hpJogador1 -= danoJogador2
        println("${pokemonJogador1.nome} HP restante: $hpJogador1")
        if (hpJogador1 <= 0) break
    }

    return when {
        hpJogador1 <= 0 && hpJogador2 <= 0 -> null
        hpJogador1 <= 0 -> pokemonJogador2
        else -> pokemonJogador1
    }
}

fun main() {

    val todosPokemons = mapOf(
        "Bulbasaur" to Pokemon("Bulbasaur", "GRAMA", 45, 49, 49),
        "Ivysaur" to Pokemon("Ivysaur", "GRAMA", 60, 62, 63),
        "Venusaur" to Pokemon("Venusaur", "GRAMA", 80, 82, 83),
        "Charmander" to Pokemon("Charmander", "FOGO", 39, 52, 43),
        "Charmeleon" to Pokemon("Charmeleon", "FOGO", 58, 64, 58),
        "Charizard" to Pokemon("Charizard", "FOGO", 78, 84, 78),
        "Squirtle" to Pokemon("Squirtle", "AGUA", 44, 48, 65),
        "Wartortle" to Pokemon("Wartortle", "AGUA", 59, 63, 80),
        "Blastoise" to Pokemon("Blastoise", "AGUA", 79, 83, 100),
        "Caterpie" to Pokemon("Caterpie", "INSETO", 45, 30, 35),
        "Metapod" to Pokemon("Metapod", "INSETO", 50, 20, 55),
        "Butterfree" to Pokemon("Butterfree", "INSETO", 60, 45, 50),
        "Weedle" to Pokemon("Weedle", "INSETO", 40, 35, 30),
        "Kakuna" to Pokemon("Kakuna", "INSETO", 45, 25, 50),
        "Beedrill" to Pokemon("Beedrill", "INSETO", 65, 90, 40),
        "Pidgey" to Pokemon("Pidgey", "NORMAL", 40, 45, 40),
        "Pidgeotto" to Pokemon("Pidgeotto", "NORMAL", 63, 60, 55),
        "Pidgeot" to Pokemon("Pidgeot", "NORMAL", 83, 80, 75),
        "Rattata" to Pokemon("Rattata", "NORMAL", 30, 56, 35),
        "Raticate" to Pokemon("Raticate", "NORMAL", 55, 81, 60),
        "Spearow" to Pokemon("Spearow", "NORMAL", 40, 60, 30),
        "Fearow" to Pokemon("Fearow", "NORMAL", 65, 90, 65),
        "Ekans" to Pokemon("Ekans", "VENENO", 35, 60, 44),
        "Arbok" to Pokemon("Arbok", "VENENO", 60, 85, 69),
        "Pikachu" to Pokemon("Pikachu", "ELETRICO", 35, 55, 40),
        "Raichu" to Pokemon("Raichu", "ELETRICO", 60, 90, 55),
        "Sandshrew" to Pokemon("Sandshrew", "TERRA", 50, 75, 85),
        "Sandslash" to Pokemon("Sandslash", "TERRA", 75, 100, 110),
        "NidoranF" to Pokemon("NidoranF", "VENENO", 55, 47, 52),
        "Nidorina" to Pokemon("Nidorina", "VENENO", 70, 62, 67),
        "Nidoqueen" to Pokemon("Nidoqueen", "VENENO", 90, 92, 87),
        "NidoranM" to Pokemon("NidoranM", "VENENO", 46, 57, 40),
        "Nidorino" to Pokemon("Nidorino", "VENENO", 61, 72, 57),
        "Nidoking" to Pokemon("Nidoking", "VENENO", 81, 102, 77),
        "Clefairy" to Pokemon("Clefairy", "NORMAL", 70, 45, 48),
        "Clefable" to Pokemon("Clefable", "NORMAL", 95, 70, 73),
        "Vulpix" to Pokemon("Vulpix", "FOGO", 38, 41, 40),
        "Ninetales" to Pokemon("Ninetales", "FOGO", 73, 76, 75),
        "Jigglypuff" to Pokemon("Jigglypuff", "NORMAL", 115, 45, 20),
        "Wigglytuff" to Pokemon("Wigglytuff", "NORMAL", 140, 70, 45),
        "Zubat" to Pokemon("Zubat", "VENENO", 40, 45, 35),
        "Golbat" to Pokemon("Golbat", "VENENO", 75, 80, 70),
        "Oddish" to Pokemon("Oddish", "GRAMA", 45, 50, 55),
        "Gloom" to Pokemon("Gloom", "GRAMA", 60, 65, 70),
        "Vileplume" to Pokemon("Vileplume", "GRAMA", 75, 80, 85),
        "Paras" to Pokemon("Paras", "INSETO", 35, 70, 55),
        "Parasect" to Pokemon("Parasect", "INSETO", 60, 95, 80),
        "Venonat" to Pokemon("Venonat", "INSETO", 60, 55, 50),
        "Venomoth" to Pokemon("Venomoth", "INSETO", 70, 65, 60),
        "Diglett" to Pokemon("Diglett", "TERRA", 10, 55, 25),
        "Dugtrio" to Pokemon("Dugtrio", "TERRA", 35, 80, 50),
        "Meowth" to Pokemon("Meowth", "NORMAL", 40, 45, 35),
        "Persian" to Pokemon("Persian", "NORMAL", 65, 70, 60),
        "Psyduck" to Pokemon("Psyduck", "AGUA", 50, 52, 48),
        "Golduck" to Pokemon("Golduck", "AGUA", 80, 82, 78),
        "Mankey" to Pokemon("Mankey", "LUTADOR", 40, 80, 35),
        "Primeape" to Pokemon("Primeape", "LUTADOR", 65, 105, 60),
        "Growlithe" to Pokemon("Growlithe", "FOGO", 55, 70, 45),
        "Arcanine" to Pokemon("Arcanine", "FOGO", 90, 110, 80),
        "Poliwag" to Pokemon("Poliwag", "AGUA", 40, 50, 40),
        "Poliwhirl" to Pokemon("Poliwhirl", "AGUA", 65, 65, 65),
        "Poliwrath" to Pokemon("Poliwrath", "AGUA", 90, 95, 95),
        "Abra" to Pokemon("Abra", "PSIQUICO", 25, 20, 15),
        "Kadabra" to Pokemon("Kadabra", "PSIQUICO", 40, 35, 30),
        "Alakazam" to Pokemon("Alakazam", "PSIQUICO", 55, 50, 45),
        "Machop" to Pokemon("Machop", "LUTADOR", 70, 80, 50),
        "Machoke" to Pokemon("Machoke", "LUTADOR", 80, 100, 70),
        "Machamp" to Pokemon("Machamp", "LUTADOR", 90, 130, 80),
        "Bellsprout" to Pokemon("Bellsprout", "GRAMA", 50, 75, 35),
        "Weepinbell" to Pokemon("Weepinbell", "GRAMA", 65, 90, 50),
        "Victreebel" to Pokemon("Victreebel", "GRAMA", 80, 105, 65),
        "Tentacool" to Pokemon("Tentacool", "AGUA", 40, 40, 35),
        "Tentacruel" to Pokemon("Tentacruel", "AGUA", 80, 70, 65),
        "Geodude" to Pokemon("Geodude", "ROCHA", 40, 80, 100),
        "Graveler" to Pokemon("Graveler", "ROCHA", 55, 95, 115),
        "Golem" to Pokemon("Golem", "ROCHA", 80, 120, 130),
        "Ponyta" to Pokemon("Ponyta", "FOGO", 50, 85, 55),
        "Rapidash" to Pokemon("Rapidash", "FOGO", 65, 100, 70),
        "Slowpoke" to Pokemon("Slowpoke", "AGUA", 90, 65, 65),
        "Slowbro" to Pokemon("Slowbro", "AGUA", 95, 75, 110),
        "Magnemite" to Pokemon("Magnemite", "ELETRICO", 25, 35, 70),
        "Magneton" to Pokemon("Magneton", "ELETRICO", 50, 60, 95),
        "Farfetchd" to Pokemon("Farfetchd", "NORMAL", 52, 65, 55),
        "Doduo" to Pokemon("Doduo", "NORMAL", 35, 85, 45),
        "Dodrio" to Pokemon("Dodrio", "NORMAL", 60, 110, 70),
        "Seel" to Pokemon("Seel", "AGUA", 65, 45, 55),
        "Dewgong" to Pokemon("Dewgong", "AGUA", 90, 70, 80),
        "Grimer" to Pokemon("Grimer", "VENENO", 80, 80, 50),
        "Muk" to Pokemon("Muk", "VENENO", 105, 105, 75),
        "Shellder" to Pokemon("Shellder", "AGUA", 30, 65, 100),
        "Cloyster" to Pokemon("Cloyster", "AGUA", 50, 95, 180),
        "Gastly" to Pokemon("Gastly", "FANTASMA", 30, 35, 30),
        "Haunter" to Pokemon("Haunter", "FANTASMA", 45, 50, 45),
        "Gengar" to Pokemon("Gengar", "FANTASMA", 60, 65, 60),
        "Onix" to Pokemon("Onix", "ROCHA", 35, 45, 160),
        "Drowzee" to Pokemon("Drowzee", "PSIQUICO", 60, 48, 45),
        "Hypno" to Pokemon("Hypno", "PSIQUICO", 85, 73, 70),
        "Krabby" to Pokemon("Krabby", "AGUA", 30, 105, 90),
        "Kingler" to Pokemon("Kingler", "AGUA", 55, 130, 115),
        "Voltorb" to Pokemon("Voltorb", "ELETRICO", 40, 30, 50),
        "Electrode" to Pokemon("Electrode", "ELETRICO", 60, 50, 70),
        "Exeggcute" to Pokemon("Exeggcute", "GRAMA", 60, 40, 80),
        "Exeggutor" to Pokemon("Exeggutor", "GRAMA", 95, 95, 85),
        "Cubone" to Pokemon("Cubone", "TERRA", 50, 50, 95),
        "Marowak" to Pokemon("Marowak", "TERRA", 60, 80, 110),
        "Hitmonlee" to Pokemon("Hitmonlee", "LUTADOR", 50, 120, 53),
        "Hitmonchan" to Pokemon("Hitmonchan", "LUTADOR", 50, 105, 79),
        "Lickitung" to Pokemon("Lickitung", "NORMAL", 90, 55, 75),
        "Koffing" to Pokemon("Koffing", "VENENO", 40, 65, 35),
        "Weezing" to Pokemon("Weezing", "VENENO", 65, 90, 60),
        "Rhyhorn" to Pokemon("Rhyhorn", "TERRA", 80, 85, 95),
        "Rhydon" to Pokemon("Rhydon", "TERRA", 105, 130, 120),
        "Chansey" to Pokemon("Chansey", "NORMAL", 250, 5, 5),
        "Tangela" to Pokemon("Tangela", "GRAMA", 65, 55, 115),
        "Kangaskhan" to Pokemon("Kangaskhan", "NORMAL", 105, 95, 80),
        "Horsea" to Pokemon("Horsea", "AGUA", 30, 40, 70),
        "Seadra" to Pokemon("Seadra", "AGUA", 55, 65, 95),
        "Goldeen" to Pokemon("Goldeen", "AGUA", 45, 67, 60),
        "Seaking" to Pokemon("Seaking", "AGUA", 80, 92, 85),
        "Staryu" to Pokemon("Staryu", "AGUA", 30, 45, 55),
        "Starmie" to Pokemon("Starmie", "AGUA", 60, 75, 85),
        "MrMime" to Pokemon("MrMime", "PSIQUICO", 40, 45, 65),
        "Scyther" to Pokemon("Scyther", "INSETO", 70, 110, 80),
        "Jynx" to Pokemon("Jynx", "GELO", 65, 50, 35),
        "Electabuzz" to Pokemon("Electabuzz", "ELETRICO", 65, 83, 57),
        "Magmar" to Pokemon("Magmar", "FOGO", 65, 95, 57),
        "Pinsir" to Pokemon("Pinsir", "INSETO", 65, 125, 100),
        "Tauros" to Pokemon("Tauros", "NORMAL", 75, 100, 95),
        "Magikarp" to Pokemon("Magikarp", "AGUA", 20, 10, 55),
        "Gyarados" to Pokemon("Gyarados", "AGUA", 95, 125, 79),
        "Lapras" to Pokemon("Lapras", "AGUA", 130, 85, 80),
        "Ditto" to Pokemon("Ditto", "NORMAL", 48, 48, 48),
        "Eevee" to Pokemon("Eevee", "NORMAL", 55, 55, 50),
        "Vaporeon" to Pokemon("Vaporeon", "AGUA", 130, 65, 60),
        "Jolteon" to Pokemon("Jolteon", "ELETRICO", 65, 65, 60),
        "Flareon" to Pokemon("Flareon", "FOGO", 65, 130, 60),
        "Porygon" to Pokemon("Porygon", "NORMAL", 65, 60, 70),
        "Omanyte" to Pokemon("Omanyte", "ROCHA", 35, 40, 100),
        "Omastar" to Pokemon("Omastar", "ROCHA", 70, 60, 125),
        "Kabuto" to Pokemon("Kabuto", "ROCHA", 30, 80, 90),
        "Kabutops" to Pokemon("Kabutops", "ROCHA", 60, 115, 105),
        "Aerodactyl" to Pokemon("Aerodactyl", "ROCHA", 80, 105, 65),
        "Snorlax" to Pokemon("Snorlax", "NORMAL", 160, 110, 65),
        "Articuno" to Pokemon("Articuno", "GELO", 90, 85, 100),
        "Zapdos" to Pokemon("Zapdos", "ELETRICO", 90, 90, 85),
        "Moltres" to Pokemon("Moltres", "FOGO", 90, 100, 90),
        "Dratini" to Pokemon("Dratini", "DRAGAO", 41, 64, 45),
        "Dragonair" to Pokemon("Dragonair", "DRAGAO", 61, 84, 65),
        "Dragonite" to Pokemon("Dragonite", "DRAGAO", 91, 134, 95),
        "Mewtwo" to Pokemon("Mewtwo", "PSIQUICO", 106, 110, 90),
        "Mew" to Pokemon("Mew", "PSIQUICO", 100, 100, 100)

    )


    val limiteDePokemon = 5


    print("Digite o nickname do Jogador 1: ")
    val nicknameJogador1 = readln()
    val timeJogador1 = criarTime(nicknameJogador1, todosPokemons, limiteDePokemon)
    println("\nTime de $nicknameJogador1:")
    timeJogador1.forEach { println("- ${it.nome} (${it.tipo})") }


    print("\nDigite o nickname do Jogador 2: ")
    val nicknameJogador2 = readln()
    val timeJogador2 = criarTime(nicknameJogador2, todosPokemons, limiteDePokemon)
    println("\nTime de $nicknameJogador2:")
    timeJogador2.forEach { println("- ${it.nome} (${it.tipo})") }

    println("\n--- Batalha Pokémon Iniciada! ---")

    var vitoriasJogador1 = 0
    var vitoriasJogador2 = 0

    for (i in 0 until limiteDePokemon) {
        println("\n--- Partida ${i + 1} ---")
        val vencedor = batalhar(timeJogador1[i], timeJogador2[i])

        if (vencedor != null) {
            println("\n${vencedor.nome} venceu a Partida ${i + 1}!")
            if (vencedor in timeJogador1) {
                vitoriasJogador1++
            } else {
                vitoriasJogador2++
            }
        } else {
            println("\nA Partida ${i + 1} terminou em empate!")
        }
    }


    println("\n--- Resultado Final da Batalha ---")
    println("Vitórias de $nicknameJogador1: $vitoriasJogador1")
    println("Vitórias de $nicknameJogador2: $vitoriasJogador2")

    if (vitoriasJogador1 > vitoriasJogador2) {
        println("\n$nicknameJogador1 é o grande vencedor!")
    } else if (vitoriasJogador2 > vitoriasJogador1) {
        println("\n$nicknameJogador2 é o grande vencedor!")
    } else {
        println("\nA batalha terminou em um empate geral!")
    }
}
