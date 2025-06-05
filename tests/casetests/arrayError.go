package main

func main() {
    m := map[string]int{"a": 1, "b": 2}
    // Erro léxico: '@' não está mapeado em nenhum token
    m@["a"] = 3
}
