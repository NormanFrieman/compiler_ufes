package main

import "fmt"

func main() {
    s := []string{1, 2.3}
    
    for idx, lang := range s {
        fmt.Printf("%d: %s\n", idx, lang)
    }
}
