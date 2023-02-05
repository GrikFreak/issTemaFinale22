from diagrams import Cluster, Diagram, Edge
from diagrams.custom import Custom
import os
os.environ['PATH'] += os.pathsep + 'C:/Program Files/Graphviz/bin/'

graphattr = {     #https://www.graphviz.org/doc/info/attrs.html
    'fontsize': '22',
}

nodeattr = {   
    'fontsize': '22',
    'bgcolor': 'lightyellow'
}

eventedgeattr = {
    'color': 'red',
    'style': 'dotted'
}
with Diagram('sonarqak22Arch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wasteservice=Custom('wasteservice(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxsonarqak22', graph_attr=nodeattr):
          sonarqak22=Custom('sonarqak22','./qakicons/symActorSmall.png')
          sonarmastermock=Custom('sonarmastermock','./qakicons/symActorSmall.png')
          sonarsimulator=Custom('sonarsimulator(coded)','./qakicons/codedQActor.png')
          sonardatasource=Custom('sonardatasource(coded)','./qakicons/codedQActor.png')
          datacleaner=Custom('datacleaner(coded)','./qakicons/codedQActor.png')
     sonarqak22 >> Edge(color='blue', style='solid', xlabel='sonaractivate', fontcolor='blue') >> sonarsimulator
     sonarqak22 >> Edge(color='blue', style='solid', xlabel='sonaractivate', fontcolor='blue') >> sonardatasource
     sys >> Edge(color='red', style='dashed', xlabel='sonar', fontcolor='red') >> sonarqak22
     sonarqak22 >> Edge( xlabel='sonardata', **eventedgeattr, fontcolor='red') >> sys
     sonarmastermock >> Edge(color='blue', style='solid', xlabel='sonaractivate', fontcolor='blue') >> sonarqak22
diag
