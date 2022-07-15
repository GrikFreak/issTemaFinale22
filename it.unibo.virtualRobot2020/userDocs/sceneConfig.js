const config = {
  floor: {
    size: { x: 35, y: 30 },
  },
  player: {
    //position: { x: 0.5, y: 0.5 },		//CENTER
    position: { x: 0.09, y: 0.1 }, //INIT
    //position: { x: 0.8, y: 0.85 },		//END
    speed: 0.25,
  },
  sonars: [],
  movingObstacles: [],
  staticObstacles: [
    /*
   
        {
            name: "plasticBox",
            centerPosition: { x: 0.15, y: 1.0},
            size: { x: 0.24, y: 0.07}
        },

		
        {
            name: "table",
            centerPosition: { x: 0.60, y: 0.40},
            size: { x: 0.16, y: 0.14      }
		},

        {
            name: "bottle1",
            centerPosition: { x: 0.55, y: 0.8 },
            size: { x: 0.05, y: 0.05      }
		},
		
        {
            name: "bottle2",
            centerPosition: { x: 0.18, y: 0.20},
            size: { x: 0.05, y: 0.05      }
		},


 
        {
            name: "obs1",
            centerPosition: { x: 0.05, y: 0.42 }, 
            size: { x: 0.053, y: 0.041}
        },

        {
            name: "obs2",
            centerPosition: { x: 0.94, y: 0.42 }, 
            size: { x: 0.053, y: 0.041}
        },
*/
    {
      name: "wallUp",
      centerPosition: { x: 0.5, y: 1 },
      size: { x: 1, y: 0.01 },
    },
    {
      name: "wallDown",
      centerPosition: { x: 0.5, y: 0.01 },
      size: { x: 1, y: 0.01 },
    },
    {
      name: "wallLeft",
      centerPosition: { x: 0.01, y: 0.5 },
      size: { x: 0.01, y: 1 },
    },
    {
      name: "wallRight",
      centerPosition: { x: 1.0, y: 0.5 },
      size: { x: 0.01, y: 1 },
    },
  ],
};

export default config;
