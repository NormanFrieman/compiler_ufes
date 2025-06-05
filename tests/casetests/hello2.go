package main

import "fmt"

// sum recebe dois inteiros e retorna a soma deles
func sum(a, b int) int {
	return a + b
}

func main() {
	// Imprime no console
	fmt.Println("Hello, World!")

	// Declaração de variável com atribuição explícita
	var name string = "GoLang"
	fmt.Printf("Bem-vindo ao %s!\n", name)

	// Cria uma slice de inteiros
	numbers := []int { 1, 2, 3, 4, 5 }

	// Soma os elementos da slice usando um loop for
	total := 0
	for _, num := range numbers {
		total += num
	}
	fmt.Printf("A soma de %v é %d\n", numbers, total)

	// Chama a função sum
	resultado := sum(10, 20)
	fmt.Printf("A soma de 10 e 20 é %d\n", resultado)

	if (resultado > 10) {
		fmt.Println("O método sum deu certo")
	} else {
		fmt.Println("O método sum deu errado")
	}
}