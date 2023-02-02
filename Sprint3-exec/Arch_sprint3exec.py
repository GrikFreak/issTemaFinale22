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
with Diagram('sprint3execArch', show=False, outformat='png', graph_attr=graphattr) as diag:
  with Cluster('env'):
     sys = Custom('','./qakicons/system.png')
     with Cluster('ctxbasicrobot', graph_attr=nodeattr):
          pathexec=Custom('pathexec(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxledqak22', graph_attr=nodeattr):
          led=Custom('led(ext)','./qakicons/externalQActor.png')
     with Cluster('ctxwasteservice', graph_attr=nodeattr):
          wastetruckmock=Custom('wastetruckmock','./qakicons/symActorSmall.png')
          distancefilter=Custom('distancefilter','./qakicons/symActorSmall.png')
          wasteservice=Custom('wasteservice','./qakicons/symActorSmall.png')
          transporttrolley=Custom('transporttrolley','./qakicons/symActorSmall.png')
     wastetruckmock >> Edge(color='magenta', style='solid', xlabel='waste_request', fontcolor='magenta') >> wasteservice
     wastetruckmock >> Edge(color='magenta', style='solid', xlabel='free_request', fontcolor='magenta') >> wasteservice
     sys >> Edge(color='red', style='dashed', xlabel='sonardata', fontcolor='red') >> distancefilter
     distancefilter >> Edge(color='blue', style='solid', xlabel='stop', fontcolor='blue') >> wasteservice
     distancefilter >> Edge(color='blue', style='solid', xlabel='resume', fontcolor='blue') >> wasteservice
     wasteservice >> Edge(color='blue', style='solid', xlabel='led_status', fontcolor='blue') >> led
     wasteservice >> Edge(color='magenta', style='solid', xlabel='transfer_request', fontcolor='magenta') >> transporttrolley
     wasteservice >> Edge(color='magenta', style='solid', xlabel='pickup_request', fontcolor='magenta') >> transporttrolley
     wasteservice >> Edge(color='magenta', style='solid', xlabel='storage_request', fontcolor='magenta') >> transporttrolley
     wasteservice >> Edge(color='magenta', style='solid', xlabel='home_request', fontcolor='magenta') >> transporttrolley
     wasteservice >> Edge(color='blue', style='solid', xlabel='stop_trolley', fontcolor='blue') >> transporttrolley
     wasteservice >> Edge(color='blue', style='solid', xlabel='resume_trolley', fontcolor='blue') >> transporttrolley
     transporttrolley >> Edge(color='magenta', style='solid', xlabel='dopath', fontcolor='magenta') >> pathexec
     transporttrolley >> Edge( xlabel='alarm', **eventedgeattr, fontcolor='red') >> sys
diag
