package main

import "fmt"

func sum(a, b int) int {
    return a + b
}

func main() {
    nums := []int{1, 2, 3}
    for i := 0; i < len(nums); i++ {
        if nums[i]%2 == 1 {
            fmt.Println(nums[i], "is odd")
        }
    }
    // Erro léxico: '#' não faz parte do conjunto de tokens
    # este comentário causa falha
}
