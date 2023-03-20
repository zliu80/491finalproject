migrate((db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("9jkis8gsdoiyirt")

  collection.listRule = ""
  collection.viewRule = ""

  return dao.saveCollection(collection)
}, (db) => {
  const dao = new Dao(db)
  const collection = dao.findCollectionByNameOrId("9jkis8gsdoiyirt")

  collection.listRule = null
  collection.viewRule = null

  return dao.saveCollection(collection)
})
