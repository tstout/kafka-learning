while true; do
n=$((n+1))
printf 'msg %d\n' "$n"
clj -A:ktk -p 1
done
