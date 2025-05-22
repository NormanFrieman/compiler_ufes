package main

import "fmt"

func main() {
    idade := 20
    temCarteira := true

    // combina && e || numa lógica só
    if idade >= 18 && temCarteira {
        fmt.Println("Pode dirigir legalmente")
    } else if idade >= 18 && !temCarteira {
        fmt.Println("Maior de idade, mas sem carteira")
    } else {
        fmt.Println("Menor de idade")
    }
}
