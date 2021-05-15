package main

import (
	"fmt"
	"sync"
	"time"
)

var wg = sync.WaitGroup{}
var x int = 0

func main() {
	for i := 0; i < 2; i++ {
		fmt.Println("Increment Cycle #", i+1)
		wg.Add(1)
		go incrementCounter()
		wg.Add(1)
		go checkCounter()
		wg.Wait()
		x = 0
	}

}

func incrementCounter() {
	for i := 0; i < 15; i++ {
		x++
		time.Sleep(time.Second)
	}
	wg.Done()
}

func checkCounter() {
	for i := 0; i < 15; i++ {
		fmt.Print(x, " ")
		time.Sleep(time.Second)
	}
	fmt.Println()
	wg.Done()
}
