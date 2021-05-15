package main

import (
	"fmt"
	"os"
	"sort"
)

var (
	nums []int
	next int
)

func main() {

	// Get user input
	fmt.Println("Enter a series of integers separated by space and press Enter twice to submit: ")
	for {
		n, err := fmt.Scanf("%d", &next)
		if err != nil {
		}
		if n == 0 {
			break
		}
		nums = append(nums, next)
	}

	if len(nums) < 1 {
		fmt.Println("Error: No input received.")
		os.Exit(1)
	}

	s := splitArr(nums, 4)

	ch := make(chan []int)
	for i := 0; i < len(s); i++ {
		go sortArr(s[i], ch)
	}

	var sortedSlice []int
	for i := 0; i < len(s); i++ {
		sortedSlice = merge(sortedSlice, <-ch)
	}
	fmt.Println()
	fmt.Println("Output:")
	fmt.Println(sortedSlice)
}

func sortArr(a []int, ch chan []int) {
	fmt.Println("Sorting:", a)
	sort.Ints(a)
	ch <- a
}

func splitArr(a []int, n int) [][]int {
	var result [][]int
	l := len(a)
	c := (l + n - 1) / n
	for i := 0; i < len(a); i += c {
		end := i + c
		if end > l {
			end = l
		}
		result = append(result, a[i:end])
	}
	return result
}

func merge(a, b []int) []int {

	arr := make([]int, len(a)+len(b))

	j, k := 0, 0

	for i := 0; i < len(arr); i++ {
		if j >= len(a) {
			arr[i] = b[k]
			k++
			continue
		} else if k >= len(b) {
			arr[i] = a[j]
			j++
			continue
		}
		if a[j] > b[k] {
			arr[i] = b[k]
			k++
		} else {
			arr[i] = a[j]
			j++
		}
	}
	return arr
}
