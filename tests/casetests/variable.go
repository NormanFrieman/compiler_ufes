package main

import (
	"fmt"
	"strings"
)

// Função simples que retorna um valor
func dobro(x int) int {
	return x * 2
}

func main() {
	// Declaração explícita com tipo
	var a int = 10
	var b float64 = 3.14
	var c string = "Olá"
	var d bool = true

	// Inferência de tipo com :=
	e := 5
	f := 2.5
	g := "Mundo"
	h := false

	// Operações matemáticas
	soma := a + e
	produto := int(b) * a
	divisao := b / float64(e)
	resto := a % e

	// Operações lógicas
	igual := a == e
	maior := a > e
	logico := d && h

	// Chamada de função
	resultadoDobro := dobro(a)

	// Array
	var numeros [3]int = [3]int{1, 2, 3}

	// Slice
	nomes := []string{"Ana", "Bob", "Carlos"}

	// Constante
	const PI = 3.1415

	// Uso de biblioteca externa (strings)
	nomeMaiusculo := strings.ToUpper(c)

	// Exibição de todos os resultados
	fmt.Println("Variáveis básicas:", a, b, c, d)
	fmt.Println("Inferência:", e, f, g, h)
	fmt.Println("Operações matemáticas:", soma, produto, divisao, resto)
	fmt.Println("Operações lógicas:", igual, maior, logico)
	fmt.Println("Resultado de função:", resultadoDobro)
	fmt.Println("Array:", numeros)
	fmt.Println("Slice:", nomes)
	fmt.Println("Constante:", PI)
	fmt.Println("Strings manipuladas:", nomeMaiusculo)
}