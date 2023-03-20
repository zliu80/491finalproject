migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("3gc3ooe3fj4mab6")

  collection.listRule = ""
  collection.viewRule = ""

  return dao.saveCollection(collection)
}, (db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("3gc3ooe3fj4mab6")

  collection.listRule = null
  collection.viewRule = null

  return dao.saveCollection(collection)
})
