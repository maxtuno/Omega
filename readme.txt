
Copyright 2014 O. A. Riveros. All rights reserved.

The use of any part of this software, it always allowed and where nonprofit,
whether to use the software for profit contact me to oscar.riveros@gmail.com
and let's talk about a commercial license.

Omega is an ultra pure lisp dialect, inspired by the work of Gregory Chaitin,
Omega's power is unlimited, but in practice its use is reduced to scientific Research.
It can be used for example in reading the work of Chaitin, or classroom.
whatever you use it for, try to play hard.

Use: 
1) - console: java -jar Omega.jar
2) - code:    java -jar Omega.jar "The Multiverse in Omega.om"

To define functions:
0    Ω> (define (add) (' (lambda (a b) (+ a b))))
1    Ω> true
2    Ω> (add 10 20)
3    Ω> 30
4    Ω> (stop)
5    Ω> stop
6    Ω> Elapsed time is 77358 milliseconds

O. A. Riveros
http://independent.academia.edu/oarr

Gregory Chaitin
http://ufrj.academia.edu/GregoryChaitin

#####################################################
#                                                   #
#                  .d88888888b.                     #
#                 d88P"    "Y88b                    #
#                 888        888                    #
#                 Y88b      d88P                    #
#                  "88bo  od88"                     #
#                 d88888  88888b                    #
#                                                   #
# Copyright 2014 O. A. Riveros. All rights reserved.#
#             oscar.riveros@gmail.com               #
#                                                   #
#####################################################
0    Ω> OMEGA Interpreter Run
1    Ω>
2    Ω> [[[[[
3    Ω>
4    Ω>  Elementary Set Theory in OMEGA (finite sets)
5    Ω>
6    Ω> ]]]]]
7    Ω>
8    Ω>
9    Ω> [Set membership predicate:]
10   Ω>
11   Ω> define (member? e[lement] set)
12   Ω>    [Is set empty?]
13   Ω>    if atom set [then] false [else]
14   Ω>    [Is the element that we are looking for the first element?]
15   Ω>    if = e car set [then] true [else]
16   Ω>    [recursion step!]
17   Ω>    [return] (member? e cdr set)
18   Ω>
19   Ω> (define (member?) (' (lambda (e set) (if (atom set) false (if (= e (car set)) true (member? e (cdr set)))))))
20   Ω> true
21   Ω>
22   Ω> (member? 1 (' (1 2 3)))
23   Ω> true
24   Ω>
25   Ω> (member? 4 (' (1 2 3)))
26   Ω> false
27   Ω>
28   Ω>
29   Ω> [Subset predicate:]
30   Ω>
31   Ω> define (subset? set1 set2)
32   Ω>    [Is the first set empty?]
33   Ω>    if atom set1 [then] true [else]
34   Ω>    [Is the first element of the first set in the second set?]
35   Ω>    if (member? car set1 set2)
36   Ω>       [then] [recursion!] (subset? cdr set1 set2)
37   Ω>       [else] false
38   Ω>
39   Ω> (define (subset?) (' (lambda (set1 set2) (if (atom set1) true (if (member? (car set1) set2) (subset? (cdr set1) set2) false)))))
40   Ω> true
41   Ω>
42   Ω> (subset? (' (1 2)) (' (1 2 3)))
43   Ω> true
44   Ω>
45   Ω> (subset? (' (1 4)) (' (1 2 3)))
46   Ω> false
47   Ω>
48   Ω>
49   Ω> [Set union:]
50   Ω>
51   Ω> define (union x y)
52   Ω>    [Is the first set empty?]
53   Ω>    if atom x [then] [return] y [else]
54   Ω>    [Is the first element of the first set in the second set?]
55   Ω>    if (member? car x y)
56   Ω>       [then] [return] (union cdr x y)
57   Ω>       [else] [return] cons car x (union cdr x y)
58   Ω>
59   Ω> (define (union) (' (lambda (x y) (if (atom x) y (if (member? (car x) y) (union (cdr x) y) (cons (car x) (union (cdr x) y)))))))
60   Ω> true
61   Ω>
62   Ω> (union (' (1 2 3)) (' (2 3 4)))
63   Ω> (1 2 3 4)
64   Ω>
65   Ω>
66   Ω> [Union of a list of sets:]
67   Ω>
68   Ω> (define (unionl) (' (lambda (l) (if (atom l) nil (union (car l) (unionl (cdr l)))))))
69   Ω> true
70   Ω>
71   Ω> (unionl (' ((1 2) (2 3) (3 4))))
72   Ω> (1 2 3 4)
73   Ω>
74   Ω>
75   Ω> [Set intersection:]
76   Ω>
77   Ω> define (intersection x y)
78   Ω>    [Is the first set empty?]
79   Ω>    if atom x [then] [return] nil [empty set] [else]
80   Ω>    [Is the first element of the first set in the second set?]
81   Ω>    if (member? car x y)
82   Ω>       [then] [return] cons car x (intersection cdr x y)
83   Ω>       [else] [return] (intersection cdr x y)
84   Ω>
85   Ω> (define (intersection) (' (lambda (x y) (if (atom x) nil (if (member? (car x) y) (cons (car x) (intersection (cdr x) y)) (intersection (cdr x) y))))))
86   Ω> true
87   Ω>
88   Ω> (intersection (' (1 2 3)) (' (2 3 4)))
89   Ω> (2 3)
90   Ω>
91   Ω>
92   Ω> [Relative complement of two sets x and y = x - y:]
93   Ω>
94   Ω> define (complement x y)
95   Ω>    [Is the first set empty?]
96   Ω>    if atom x [then] [return] nil [empty set] [else]
97   Ω>    [Is the first element of the first set in the second set?]
98   Ω>    if (member? car x y)
99   Ω>       [then] [return] (complement cdr x y)
100  Ω>       [else] [return] cons car x (complement cdr x y)
101  Ω>
102  Ω> (define (complement) (' (lambda (x y) (if (atom x) nil (if (member? (car x) y) (complement (cdr x) y) (cons (car x) (complement (cdr x) y)))))))
103  Ω> true
104  Ω>
105  Ω> (complement (' (1 2 3)) (' (2 3 4)))
106  Ω> (1)
107  Ω>
108  Ω> [Cartesian product of an element with a list:]
109  Ω>
110  Ω> define (product1 e y)
111  Ω>    if atom y
112  Ω>       [then] nil
113  Ω>       [else] cons cons e cons car y nil (product1 e cdr y)
114  Ω>
115  Ω> (define (product1) (' (lambda (e y) (if (atom y) nil (cons (cons e (cons (car y) nil)) (product1 e (cdr y)))))))
116  Ω> true
117  Ω>
118  Ω> (product1 3 (' (4 5 6)))
119  Ω> ((3 4) (3 5) (3 6))
120  Ω>
121  Ω>
122  Ω> [Cartesian product of two sets = set of ordered pairs:]
123  Ω>
124  Ω> define (product x y)
125  Ω>    [Is the first set empty?]
126  Ω>    if atom x [then] [return] nil [empty set] [else]
127  Ω>    [return] (union (product1 car x y) (product cdr x y))
128  Ω>
129  Ω> (define (product) ('(lambda (x y) (if (atom x) nil (union (product1 (car x) y) (product (cdr x) y))))))
130  Ω> true
131  Ω>
132  Ω> (product (' (1 2 3)) (' (x y z)))
133  Ω> ((1 x) (1 y) (1 z) (2 x) (2 y) (2 z) (3 x) (3 y) (3 z))
134  Ω>
135  Ω>
136  Ω> [Product of an element with a list of sets:]
137  Ω>
138  Ω> define (product2 e y)
139  Ω>    if atom y
140  Ω>       [then] nil
141  Ω>       [else] cons cons e car y (product2 e cdr y)
142  Ω>
143  Ω> (define product2 (' (lambda (e y) (if (atom y) nil (cons (cons e (car y)) (product2 e (cdr y)))))))
144  Ω> true
145  Ω>
146  Ω> (product2 3 (' ((4 5) (5 6) (6 7))))
147  Ω> ((3 4 5) (3 5 6) (3 6 7))
148  Ω>
149  Ω>
150  Ω> [Set of all subsets of a given set:]
151  Ω>
152  Ω> define (subsets x)
153  Ω>    if atom x
154  Ω>       [then] '(()) [else]
155  Ω>       let y [be] (subsets cdr x) [in]
156  Ω>       (union y (product2 car x y))
157  Ω>
158  Ω> (define (subsets) (' (lambda (x) (if (atom x) (' (())) ((' (lambda (y) (union y (product2 (car x) y)))) (subsets (cdr x)))))))
159  Ω> true
160  Ω>
161  Ω> (subsets (' (1 2 3)))
162  Ω> (() (3) (2) (2 3) (1) (1 3) (1 2) (1 2 3))
163  Ω>
164  Ω> (length (subsets (' (1 2 3))))
165  Ω> 8
166  Ω>
167  Ω> (subsets (' (1 2 3 4))) (() (4) (3) (3 4) (2) (2 4) (2 3) (2 3 4) (1) (1 4) (1 3) (1 3 4) (1 2) (1 2 4) (1 2 3) (1 2 3 4))
168  Ω> (() (4) (3) (3 4) (2) (2 4) (2 3) (2 3 4) (1) (1 4) (1 3) (1 3 4) (1 2) (1 2 4) (1 2 3) (1 2 3 4))
169  Ω>
170  Ω> (length (subsets (' (1 2 3 4))))
171  Ω> 16
172  Ω>
173  Ω> End of OMEGA Run
174  Ω>
175  Ω> Elapsed time is 195 milliseconds
