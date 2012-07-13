function main()
{
   // Repository Library root node
   var rootNode = "alfresco://company/home",
      repoConfig = config.scoped["RepositoryLibrary"]["root-node"];
   
   if (repoConfig !== null)
   {
      rootNode = repoConfig.value;
   }
   
   model.rootNode = rootNode;
   
   // Widget instantiation metadata...
   var filters = config.scoped['DocumentLibrary']['filters'],
       maxTagCount = filters.getChildValue('maximum-tag-count');
   
   if (maxTagCount == null)
   {
      maxTagCount = "100";
   }
   
   model.widgets = [];
   var tagFilter = {
      name : "Alfresco.TagFilter",
      assignTo : "tagFilter",
      options : {
         siteId : (page.url.templateArgs.site != null) ? page.url.templateArgs.site : "",
         containerId : (template.properties.container != null) ? template.properties.container : "",
         rootNode : model.rootNode,
         numTags : maxTagCount
      }
   };
   model.widgets.push(tagFilter);
}

main();
