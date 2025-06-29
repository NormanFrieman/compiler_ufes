package main

import "fmt"

func main() {
    const s := []string{}
    
    for idx, lang := range s {
        fmt.Printf("%d: %s\n", idx, lang)
    }
}
