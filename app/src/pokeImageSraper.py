import json


main_url = "https://img.pokemondb.net/artwork/"


pokemon = {"pokemon":[]}
with open("originalPokemon.csv", "r") as f:
    raw = f.read().split('\n')
    for line in raw[1:]:
        if line:
            pokeDict = {}
            l = line.split(',')
            pokeDict["name"] = l[0].strip()
            pokeDict["url"] = main_url+l[0].lower().strip()+".jpg"
            pokeDict["idx"] = l[1]
            pokemon["pokemon"].append(pokeDict)

with open("orginalPokemon.json", "w") as f:
    json.dump(pokemon,f)